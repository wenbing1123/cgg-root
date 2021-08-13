package com.cgg.service.user.security.authentication;

import com.cgg.service.user.security.authentication.jwt.JwtAuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
public class RedisServerSecurityContextRepository implements ServerSecurityContextRepository {

    @Resource
    private MultiAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // TODO 保存redis
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            return  authenticationManager.authenticate(new JwtAuthorizationToken(authToken)).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
