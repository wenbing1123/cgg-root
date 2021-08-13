package com.cgg.framework.config.properties;

import lombok.Data;

@Data
public class WechatProperties {

    private Login login;
    private Pay pay;

    @Data
    public static class Login {
        private String appId;
        private String appSecret;
        private String callbackUrl;
    }

    @Data
    public static class Pay {
        private String appId;
        private String mchId;
        private String mchKey;
        private String notifyUrl; //支付回调地址
    }

}
