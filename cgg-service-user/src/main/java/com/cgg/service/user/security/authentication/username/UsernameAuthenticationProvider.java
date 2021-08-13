package com.cgg.service.user.security.authentication.username;

import com.cgg.framework.exception.ArgumentException;
import com.cgg.framework.exception.BizFailException;
import com.cgg.framework.redis.RedisManager;
import com.cgg.service.baseconfig.service.ConfigService;
import com.cgg.service.user.constants.UserConstants;
import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.enums.AuthType;
import com.cgg.service.user.enums.UserStatus;
import com.cgg.service.user.exception.*;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.AuthenticationProvider;
import com.cgg.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.annotation.Resource;

public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisManager redisManager;
    @Resource
    private ConfigService configService;

    @Override
    public boolean isSupported(Authentication authentication) {
        return authentication instanceof UsernameAuthorizationToken;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        UsernameAuthorizationToken token = (UsernameAuthorizationToken) authentication;

        Mono<Long> loginErrorCountToLock =
                configService.getAsLong(UserConstants.CFG_NAME_LOGIN_ERROR_COUNT_TO_LOCK, 5L);
        Mono<Long> loginErrorCountToCaptcha =
                configService.getAsLong(UserConstants.CFG_NAME_LOGIN_ERROR_COUNT_TO_CAPTCHA, 3L);
        Mono<Long> loginErrorTimeWindow =
                configService.getAsLong(UserConstants.CFG_NAME_LOGIN_ERROR_TIME_WINDOW, 5 * 60L);

        return redisManager.getWindowCount(UserConstants.REDIS_COLLECTION_LOGIN_ERROR_COUNT + token.getUsername())
                .switchIfEmpty(Mono.just(0L))
                .zipWith(loginErrorCountToLock)
                .filter(x -> x.getT1().compareTo(x.getT2()) < 0)
                .switchIfEmpty(Mono.error(new LockedException(AuthError.of(AuthType.USERNAME, token.getUsername()))))
                .map(Tuple2::getT1)
                .zipWith(loginErrorCountToCaptcha)
                .flatMap(t -> {
                    if (StringUtils.isNotBlank(token.getCaptchaKey()) || t.getT1().compareTo(t.getT2()) >= 0) { // 传入验证码或错误次数超过显示验证码的次数必校验验证码
                        return Mono
                                .justOrEmpty(token.getCaptchaKey())
                                .switchIfEmpty(Mono.error(new ArgumentException("验证码为空")))
                                .flatMap(x -> redisManager.get(UserConstants.REDIS_COLLECTION_LOGIN_CAPTCHA + x))
                                .cast(String.class)
                                .filter(x -> StringUtils.equals(x, token.getCaptcha()))
                                .switchIfEmpty(Mono.error(new BizFailException("验证码错误")))
                                .map(x -> redisManager.del(UserConstants.REDIS_COLLECTION_LOGIN_CAPTCHA + x))
                                .then(retrieveUser(token.getUsername()));
                    }
                    return retrieveUser(token.getUsername());
                })
                .flatMap(user -> {
                    if (this.passwordEncoder.matches(token.getPassword(), user.getPassword())) {
                        return Mono.just(user);
                    }

                    return Mono.zip(loginErrorTimeWindow, loginErrorCountToLock)
                            .flatMap(t -> redisManager
                                    .incWindowCount(UserConstants.REDIS_COLLECTION_LOGIN_ERROR_COUNT + token.getUsername(), t.getT1(), t.getT2())
                                    .map(errorCount -> {
                                        if (errorCount >= t.getT2()) {
                                            userService.update(User
                                                                    .builder()
                                                                    .id(user.getId())
                                                                    .status(user.getStatus())
                                                                    .build())
                                                        .thenReturn(errorCount);
                                        }
                                        return errorCount;
                                    }))
                            .flatMap(errorCount -> Mono.error(new BadCredentialsException(
                                    AuthError.of(AuthType.USERNAME, token.getUsername(),
                                            AuthError.ResData
                                                    .builder()
                                                    .errorCount(errorCount.intValue())
                                                    .build()))));
                })
                .map(user -> new UsernameAuthorizationToken(SecurityUser
                        .builder()
                        .userId(Long.toString(user.getId()))
                        .username(user.getUsername())
                        .build()));
    }

    private Mono<User> retrieveUser(String username) {
        return userService
                .findByUsername(username)
                .switchIfEmpty(userService.findByPhone(username))
                .switchIfEmpty(userService.findByEmail(username))
                .switchIfEmpty(Mono.error(() -> new UsernameNotFoundException(AuthError.of(AuthType.USERNAME, username))))
                .doOnNext(this::checkUser);
    }

    private void checkUser(User user) {
        if (UserStatus.isLocked(user.getStatus())) {
            throw new LockedException(AuthError.of(AuthType.USERNAME, user.getUsername()));
        }
        if (UserStatus.isDisabled(user.getStatus())) {
            throw new DisabledException(AuthError.of(AuthType.USERNAME, user.getUsername()));
        }
    }
}
