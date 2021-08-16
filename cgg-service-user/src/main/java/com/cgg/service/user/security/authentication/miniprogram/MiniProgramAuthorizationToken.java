package com.cgg.service.user.security.authentication.miniprogram;

import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.BaseAuthenticationToken;
import lombok.Getter;

@Getter
public class MiniProgramAuthorizationToken extends BaseAuthenticationToken {

    private final String code;
    private final String encryptedData;
    private final String iv;

    public MiniProgramAuthorizationToken(String code, String encryptedData, String iv) {
        this.code = code;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    public MiniProgramAuthorizationToken(SecurityUser user) {
        super(user);
        this.code = null;
        this.encryptedData = null;
        this.iv = null;
    }

}
