package com.cgg.service.user.security.authentication;

import com.cgg.service.user.security.SecurityUser;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public abstract class BaseAuthenticationToken extends AbstractAuthenticationToken {

    private SecurityUser user;

    public BaseAuthenticationToken() {
        super(null);
        setAuthenticated(false);
    }

    public BaseAuthenticationToken(SecurityUser user) {
        super(user.getAuthorities());
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
