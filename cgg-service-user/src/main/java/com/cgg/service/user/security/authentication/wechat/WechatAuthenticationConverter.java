package com.cgg.service.user.security.authentication.wechat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class WechatAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String code = request.getQueryParams().getFirst("code");
        if (StringUtils.isEmpty(code)) {
            return Mono.empty();
        }

        return Mono.just(new WechatAuthorizationToken(code));
    }

}
