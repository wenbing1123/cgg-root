package com.cgg.framework.config;

import com.cgg.framework.config.properties.OssProperties;
import com.cgg.framework.oss.OssManager;
import com.cgg.framework.oss.OssManagerImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssProperties ossProperties() {
        return new OssProperties();
    }

    @Bean
    public OssManager ossManager(OssProperties ossProperties) {
        return new OssManagerImpl(
                ossProperties.getOssAddress(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
    }

}
