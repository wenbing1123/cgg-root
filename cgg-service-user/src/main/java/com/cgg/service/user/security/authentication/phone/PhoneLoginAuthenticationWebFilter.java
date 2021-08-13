package com.cgg.service.user.security.authentication.phone;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

public class PhoneLoginAuthenticationWebFilter extends AuthenticationWebFilter {

    public static final String DEFAULT_LOGIN_URL = "/login/phone";

    public PhoneLoginAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationMatcher(new PathPatternParserServerWebExchangeMatcher(DEFAULT_LOGIN_URL));
        setServerAuthenticationConverter(new PhoneAuthorizationConverter());
    }
}
