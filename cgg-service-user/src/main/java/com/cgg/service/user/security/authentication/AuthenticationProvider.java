package com.cgg.service.user.security.authentication;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface AuthenticationProvider {

    boolean isSupported(Authentication authentication);
    Mono<Authentication> authenticate(Authentication authentication);

}
