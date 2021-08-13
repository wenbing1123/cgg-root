package com.cgg.service.user.security.authentication.username;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

public class UsernameLoginAuthenticationWebFilter extends AuthenticationWebFilter {

    public static final String DEFAULT_LOGIN_URL = "/login/username";

    public UsernameLoginAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationMatcher(new PathPatternParserServerWebExchangeMatcher(DEFAULT_LOGIN_URL));
        setServerAuthenticationConverter(new UsernameAuthenticationConverter());
    }

}
