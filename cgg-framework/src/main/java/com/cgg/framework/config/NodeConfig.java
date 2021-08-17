package com.cgg.framework.config;

import com.cgg.framework.config.properties.NodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"file:${node.config}"}, ignoreResourceNotFound = true)
public class NodeConfig {

    @ConfigurationProperties(prefix = "node")
    @Bean
    NodeProperties nodeProperties() {
        return new NodeProperties();
    }

}
