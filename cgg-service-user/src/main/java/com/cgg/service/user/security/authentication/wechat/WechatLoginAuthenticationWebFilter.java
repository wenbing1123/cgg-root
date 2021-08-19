package com.cgg.service.user.security.authentication.wechat;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

public class WechatLoginAuthenticationWebFilter extends AuthenticationWebFilter {

    public static final String DEFAULT_LOGIN_URL = "v1/login/wechat";

    public WechatLoginAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationMatcher(new PathPatternParserServerWebExchangeMatcher(DEFAULT_LOGIN_URL));
        setServerAuthenticationConverter(new WechatAuthenticationConverter());
    }
}
