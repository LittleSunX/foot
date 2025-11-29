package com.sun.foot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(SnowflakeProperties properties) {
        return new SnowflakeIdWorker(properties.getDatacenterId());
    }
}
