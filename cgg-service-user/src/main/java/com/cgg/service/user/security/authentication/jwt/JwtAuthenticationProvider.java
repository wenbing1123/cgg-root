package com.cgg.service.user.security.authentication.jwt;

import com.cgg.service.user.security.authentication.AuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private JwtManager jwtManager;

    @Override
    public boolean isSupported(Authentication authentication) {
        return authentication instanceof JwtAuthorizationToken;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtAuthorizationToken.class)
                .map(auth -> jwtManager.verifyToken(auth.getToken()))
                .map(JwtAuthorizationToken::new);
    }
}
