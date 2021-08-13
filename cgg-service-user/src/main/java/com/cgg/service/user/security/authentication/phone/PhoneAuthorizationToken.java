package com.cgg.service.user.security.authentication.phone;

import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.BaseAuthenticationToken;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class PhoneAuthorizationToken extends BaseAuthenticationToken {

    private final String phone; // 手机号
    private final String code; // 短信检验码

    public PhoneAuthorizationToken(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public PhoneAuthorizationToken(SecurityUser user) {
        super(user);
        this.phone = null;
        this.code = null;
    }

}
