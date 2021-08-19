package com.cgg.service.account.api;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface SmsService {

    Mono<Boolean> sendMessage(String phone, String tplCode, Map<String, Object>... params);

    Mono<Boolean> sendVerifyCode(String phone, String tplCode);

    Mono<Boolean> checkVerifyCode(String phone, String tplCode, String code);

}
