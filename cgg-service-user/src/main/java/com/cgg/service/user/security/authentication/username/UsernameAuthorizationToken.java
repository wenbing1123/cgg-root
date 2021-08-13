package com.cgg.service.user.security.authentication.username;

import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.BaseAuthenticationToken;
import lombok.Getter;

@Getter
public class UsernameAuthorizationToken extends BaseAuthenticationToken {

    private final String username;
    private final String password;
    private final String captcha;
    private final String captchaKey;

    public UsernameAuthorizationToken(String username, String password, String captcha, String captchaKey) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
        this.captchaKey = captchaKey;
    }

    public UsernameAuthorizationToken(SecurityUser user) {
        super(user);
        this.username = null;
        this.password = null;
        this.captcha = null;
        this.captchaKey = null;
    }

}
