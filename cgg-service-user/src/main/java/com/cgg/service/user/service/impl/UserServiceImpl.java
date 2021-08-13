package com.cgg.service.user.service.impl;

import com.cgg.framework.ensure.Ensure;
import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.dao.entity.UserLoginLog;
import com.cgg.service.user.dao.entity.UserWechat;
import com.cgg.service.user.dao.repository.UserLoginLogRepository;
import com.cgg.service.user.dao.repository.UserRepository;
import com.cgg.service.user.dao.repository.UserWechatRepository;
import com.cgg.service.user.service.UserService;
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
    public Mono<User> save(User user) {
        if (StringUtils.isNotBlank(user.getPhone()) && StringUtils.isBlank(user.getUsername())) { // 手机号注册
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
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Mono<UserWechat> saveWechat(UserWechat wechat) {
        return userRepository
                .save(User
                        .builder()
                        .username(NameUtils.randomUsername(8))
                        .nickName(wechat.getNickName())
                        .avatarUrl(wechat.getAvatarUrl())
                        .build())
                .flatMap(u -> {
                    wechat.setUserId(u.getId()); // 设置用户id
                    return userWechatRepository.save(wechat);
                });
    }

    @Override
    public Mono<UserLoginLog> saveLoginLog(UserLoginLog loginLog) {
        return userLoginLogRepository.save(loginLog);
    }

    @Override
    public Mono<User> update(User user) {
        return userRepository.findById(user.getId()).flatMap(u -> {
            if (StringUtils.isNotBlank(user.getPhone())) {
                u.setPhone(user.getPhone());
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                u.setEmail(user.getEmail());
            }
            if (StringUtils.isNotBlank(user.getNickName())) {
                u.setNickName(user.getNickName());
            }
            if (StringUtils.isNotBlank(user.getAvatarUrl())) {
                u.setAvatarUrl(user.getAvatarUrl());
            }
            if (user.getStatus() != null) {
                u.setStatus(user.getStatus());
            }
            return userRepository.save(u);
        });
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findOne(Example.of(User
                .builder()
                .username(username)
                .build()));
    }

    @Override
    public Mono<User> findByPhone(String phone) {
        return userRepository.findOne(Example.of(User
                .builder()
                .phone(phone)
                .build()));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findOne(Example.of(User
                .builder()
                .email(email)
                .build()));
    }

    @Override
    public Mono<UserWechat> findByOpenId(String openId) {
        return userWechatRepository.findOne(Example.of(UserWechat
                .builder()
                .openId(openId)
                .build()));
    }
}
