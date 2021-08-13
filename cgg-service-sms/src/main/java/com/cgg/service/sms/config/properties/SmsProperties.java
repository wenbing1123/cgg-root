package com.cgg.service.sms.config.properties;

import lombok.Data;

@Data
public class SmsProperties {

    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private Integer codeLength;
    private Integer codeDuration;

}
