package com.cgg.service.user.security.authentication.username;

import com.cgg.framework.ensure.Ensure;
import com.cgg.service.user.security.authentication.phone.PhoneAuthorizationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class UsernameAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return exchange.getFormData().map(this::createAuthentication);
    }

    private UsernameAuthorizationToken createAuthentication(MultiValueMap<String, String> data) {
        String username = data.getFirst("username");
        String password = data.getFirst("password");
        String captcha = data.getFirst("captcha");
        String captchaKey = data.getFirst("captchaKey");
        Ensure.paramNotBlank(username, "用户名为空");
        Ensure.paramNotBlank(password, "密码为空");
        return new UsernameAuthorizationToken(username, password, captcha, captchaKey);
    }
}
