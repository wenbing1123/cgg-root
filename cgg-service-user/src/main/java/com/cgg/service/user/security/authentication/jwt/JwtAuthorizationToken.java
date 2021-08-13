package com.cgg.service.user.security.authentication.jwt;

import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.BaseAuthenticationToken;
import lombok.Getter;

@Getter
public class JwtAuthorizationToken extends BaseAuthenticationToken {

    private final String token;

    public JwtAuthorizationToken(String token) {
        this.token = token;
    }

    public JwtAuthorizationToken(SecurityUser user) {
        super(user);
        this.token = null;
    }


}
