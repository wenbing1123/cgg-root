package com.cgg.service.sms.config;

import com.cgg.service.sms.config.properties.SmsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @ConfigurationProperties(prefix = "sms")
    @Bean
    public SmsProperties smsProperties() {
        return new SmsProperties();
    }

}
