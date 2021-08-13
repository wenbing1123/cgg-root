package com.cgg.service.user.security.authentication.wechat;

import lombok.Data;

@Data
public class WechatToken {

    private String openId;
    private String accessToken;

}
