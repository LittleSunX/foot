package com.sun.foot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Snowflake 配置属性
 * datacenterId 由 YML 指定，不同机房配置不同
 */
@Component
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeProperties {

    /**
     * 数据中心 ID（0~31）
     * 一个机房配置一个 ID
     */
    private int datacenterId;

    public int getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(int datacenterId) {
        this.datacenterId = datacenterId;
    }
}
