package com.cgg.service.user.security.authentication.phone;

import com.cgg.framework.ensure.Ensure;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class PhoneAuthorizationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return exchange.getFormData().map(this::createAuthentication);
    }

    private PhoneAuthorizationToken createAuthentication(MultiValueMap<String, String> data) {
        String phone = data.getFirst("phone");
        String code = data.getFirst("code");
        Ensure.paramNotBlank(phone, "手机号为空");
        Ensure.paramNotBlank(code, "短信验证码为空");
        return new PhoneAuthorizationToken(phone, code);
    }
}
