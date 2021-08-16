package com.cgg.service.user.security.authentication.wechat;

import com.cgg.framework.ensure.Ensure;
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
        Ensure.paramNotBlank(code, "参数错误");
        return Mono.just(new WechatAuthorizationToken(code));
    }

}
