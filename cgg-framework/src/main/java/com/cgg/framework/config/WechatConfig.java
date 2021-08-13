package com.cgg.framework.config;

import com.cgg.framework.config.properties.WechatProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatConfig {

    @ConfigurationProperties(prefix = "wechat")
    @Bean
    public WechatProperties wechatProperties() {
        return new WechatProperties();
    }

}
