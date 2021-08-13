package com.cgg.service.user.config.properties;

import lombok.Data;

@Data
public class JwtProperties {

    private String issuer; // 颁发者
    private Long expiredSeconds; //过期秒数
    private String keyId;
    private String privateKey;
    private String publicKey;

}
