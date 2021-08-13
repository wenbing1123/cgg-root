package com.cgg.service.user.security.authentication.phone;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.exception.BizFailException;
import com.cgg.service.baseconfig.service.ConfigService;
import com.cgg.service.sms.service.SmsService;
import com.cgg.service.user.constants.UserConstants;
import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.enums.AuthType;
import com.cgg.service.user.enums.UserStatus;
import com.cgg.service.user.exception.BadCodeException;
import com.cgg.service.user.exception.DisabledException;
import com.cgg.service.user.exception.LockedException;
import com.cgg.service.user.exception.UsernameNotFoundException;
import com.cgg.service.user.exception.base.AuthError;
import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.AuthenticationProvider;
import com.cgg.service.user.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

public class PhoneAuthorizationProvider implements AuthenticationProvider {

    @Resource
    private SmsService smsService;
    @Resource
    private UserService userService;
    @Resource
    private ConfigService configService;

    @Override
    public boolean isSupported(Authentication authentication) {
        return authentication instanceof PhoneAuthorizationToken;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        PhoneAuthorizationToken token = (PhoneAuthorizationToken) authentication;
        return smsService.checkVerifyCode(token.getPhone(), UserConstants.TPL_CODE_LOGIN, token.getCode())
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.error(BadCodeException::new))
                .flatMap(b -> configService.getAsBoolean(UserConstants.CFG_NAME_LOGIN_AUTO_REGISTER, true))
                .flatMap(b -> {
                    if (BooleanUtils.isTrue(b)) { //注册
                        return retrieveUser(token.getPhone()).switchIfEmpty(registerUser(token.getPhone()));
                    }
                    return retrieveUser(token.getPhone());
                })
                .map(this::createToken);
    }

    private Mono<User> retrieveUser(String phone) {
        return userService
                .findByPhone(phone)
                .switchIfEmpty(Mono.error(() -> new UsernameNotFoundException("该手机号未注册", AuthError.of(AuthType.PHONE, phone))))
                .doOnNext(this::checkUser);
    }

    private void checkUser(User user) {
        if (UserStatus.isLocked(user.getStatus())) {
            throw new LockedException(AuthError.of(AuthType.PHONE, user.getUsername()));
        }
        if (UserStatus.isDisabled(user.getStatus())) {
            throw new DisabledException(AuthError.of(AuthType.PHONE, user.getUsername()));
        }
    }

    private Mono<User> registerUser(String phone) {
       return userService.save(User
               .builder()
               .phone(phone)
               .build());
    }

    private PhoneAuthorizationToken createToken(User user) {
        return new PhoneAuthorizationToken(SecurityUser
                .builder()
                .userId(Long.toString(user.getId()))
                .username(user.getUsername())
                .build());
    }

}
