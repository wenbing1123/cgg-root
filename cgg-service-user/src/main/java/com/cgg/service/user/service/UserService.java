package com.cgg.service.user.service;

import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.dao.entity.UserLoginLog;
import com.cgg.service.user.dao.entity.UserWechat;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);
    Mono<UserWechat> saveWechat(UserWechat wechat);
    Mono<UserLoginLog> saveLoginLog(UserLoginLog loginLog);
    Mono<User> update(User user);

    Mono<User> findByUsername(String username);
    Mono<User> findByPhone(String phone);
    Mono<User> findByEmail(String email);
    Mono<UserWechat> findByOpenId(String openId);



}
