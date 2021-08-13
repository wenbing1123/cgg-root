package com.cgg.service.user.security.authentication.wechat;

import com.cgg.service.user.security.SecurityUser;
import com.cgg.service.user.security.authentication.BaseAuthenticationToken;
import lombok.Getter;

@Getter
public class WechatAuthorizationToken extends BaseAuthenticationToken {

    private final String code; // 微信授权码

    public WechatAuthorizationToken(String code) {
        this.code = code;
    }

    public WechatAuthorizationToken(SecurityUser user) {
        super(user);
        this.code = null;
    }

}
