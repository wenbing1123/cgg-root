package com.cgg.service.user.security;

import com.cgg.service.user.security.authentication.jwt.JwtAuthorizationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class SecurityUtils {

    public static Mono<SecurityUser> getUser() {
        return ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .cast(JwtAuthorizationToken.class)
                .map(JwtAuthorizationToken::getUser);
    }

}
