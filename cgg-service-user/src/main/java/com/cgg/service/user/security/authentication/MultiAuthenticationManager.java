package com.cgg.service.user.security.authentication;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 多认证方式
 */
public class MultiAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private List<AuthenticationProvider> authenticationProviders;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Flux.fromIterable(authenticationProviders)
                 .filter(x -> x.isSupported(authentication))
                 .single()
                 .flatMap(x -> x.authenticate(authentication));
    }


}
