package com.cgg.service.user.api;

import com.cgg.service.user.dto.command.LoginLogSaveCommand;
import com.cgg.service.user.dto.command.UserSaveCommand;
import com.cgg.service.user.dto.command.UserWechatSaveCommand;
import com.cgg.service.user.dto.response.LoginLogSaveResult;
import com.cgg.service.user.dto.response.UserResult;
import com.cgg.service.user.dto.response.UserWechatResult;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserResult> save(UserSaveCommand cmd);
    Mono<UserWechatResult> saveWechat(UserWechatSaveCommand cmd);
    Mono<LoginLogSaveResult> saveLoginLog(LoginLogSaveCommand cmd);
    Mono<UserResult> update(UserSaveCommand cmd);

    Mono<UserResult> findByUsername(String username);
    Mono<UserResult> findByPhone(String phone);
    Mono<UserResult> findByEmail(String email);
    Mono<UserWechatResult> findByOpenId(String openId);

}
