package com.cgg.service.user.service;

import com.cgg.framework.ensure.Ensure;
import com.cgg.service.user.api.UserService;
import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.dao.entity.UserLoginLog;
import com.cgg.service.user.dao.entity.UserWechat;
import com.cgg.service.user.dao.repository.UserLoginLogRepository;
import com.cgg.service.user.dao.repository.UserRepository;
import com.cgg.service.user.dao.repository.UserWechatRepository;
import com.cgg.service.user.dto.command.LoginLogSaveCommand;
import com.cgg.service.user.dto.command.UserSaveCommand;
import com.cgg.service.user.dto.command.UserWechatSaveCommand;
import com.cgg.service.user.dto.response.LoginLogSaveResult;
import com.cgg.service.user.dto.response.UserResult;
import com.cgg.service.user.dto.response.UserWechatResult;
import com.cgg.service.user.utils.NameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserWechatRepository userWechatRepository;
    @Resource
    private UserLoginLogRepository userLoginLogRepository;

    @Override
    public Mono<UserResult> save(UserSaveCommand cmd) {
        User user = new User();
        if (StringUtils.isNotBlank(cmd.getPhone()) && StringUtils.isBlank(cmd.getUsername())) { // 手机号注册
            user.setUsername(NameUtils.randomUsername(8));
            user.setPassword(StringUtils.EMPTY);
            user.setEmail(StringUtils.EMPTY);
            user.setNickName(StringUtils.EMPTY);
            user.setAvatarUrl(StringUtils.EMPTY);
        } else {
            Ensure.paramNotBlank(user.getUsername(), "用户名不允许为空");
            Ensure.paramNotBlank(user.getPassword(), "密码不允许为空");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user)
                .map(u -> UserResult
                        .builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .build());
    }

    @Override
    @Transactional
    public Mono<UserWechatResult> saveWechat(UserWechatSaveCommand cmd) {
        return userRepository
                .save(User
                        .builder()
                        .username(NameUtils.randomUsername(8))
                        .nickName(cmd.getNickName())
                        .avatarUrl(cmd.getAvatarUrl())
                        .build())
                .flatMap(u -> userWechatRepository
                        .save(cmd.covt(UserWechat.class))
                        .map(w -> UserWechatResult
                                .builder()
                                .userId(w.getUserId())
                                .build()));
    }

    @Override
    public Mono<LoginLogSaveResult> saveLoginLog(LoginLogSaveCommand cmd) {
        return userLoginLogRepository
                .save(cmd.covt(UserLoginLog.class))
                .map(log -> log.covt(LoginLogSaveResult.class));
    }

    @Override
    public Mono<UserResult> update(UserSaveCommand cmd) {
        return userRepository.findById(cmd.getUserId()).flatMap(u -> {
            if (StringUtils.isNotBlank(cmd.getPhone())) {
                u.setPhone(cmd.getPhone());
            }
            if (StringUtils.isNotBlank(cmd.getEmail())) {
                u.setEmail(cmd.getEmail());
            }
            if (StringUtils.isNotBlank(cmd.getNickName())) {
                u.setNickName(cmd.getNickName());
            }
            if (StringUtils.isNotBlank(cmd.getAvatarUrl())) {
                u.setAvatarUrl(cmd.getAvatarUrl());
            }
            if (cmd.getStatus() != null) {
                u.setStatus(cmd.getStatus());
            }
            return userRepository.save(u);
        }).map(user -> user.covt(UserResult.class));
    }

    @Override
    public Mono<UserResult> findByUsername(String username) {
        return userRepository
                .findOne(Example.of(User
                        .builder()
                        .username(username)
                        .build()))
                .map(user -> user.covt(UserResult.class));
    }

    @Override
    public Mono<UserResult> findByPhone(String phone) {
        return userRepository
                .findOne(Example.of(User
                        .builder()
                        .phone(phone)
                        .build()))
                .map(user -> user.covt(UserResult.class));
    }

    @Override
    public Mono<UserResult> findByEmail(String email) {
        return userRepository
                .findOne(Example.of(User
                        .builder()
                        .email(email)
                        .build()))
                .map(user -> user.covt(UserResult.class));
    }

    @Override
    public Mono<UserWechatResult> findByOpenId(String openId) {
        return userWechatRepository
                .findOne(Example.of(UserWechat
                        .builder()
                        .openId(openId)
                        .build()))
                .map(uw -> uw.covt(UserWechatResult.class));
    }
}
