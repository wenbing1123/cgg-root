package com.cgg.service.user.config;

import com.cgg.service.user.config.properties.JwtProperties;
import com.cgg.service.user.security.authentication.jwt.JwtManager;
import com.cgg.service.user.security.authentication.jwt.JwtManagerJose4jImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @ConfigurationProperties(prefix = "jwt")
    @Bean
    JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    JwtManager jwtManager() {
        return new JwtManagerJose4jImpl(jwtProperties());
    }

}
