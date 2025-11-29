package com.sun.foot.config;

import com.sun.crypto.AesUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String encryptedPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    // 新增配置：密钥文件路径，可放在 application.yml 或 application.properties
    @Value("${encryption.key.path}")
    private String keyFilePath;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(decryptPassword(encryptedPassword));
        ds.setDriverClassName(driverClassName);
        return ds;
    }

    private String decryptPassword(String encrypted) {
        try {
            // 从文件系统读取 secret.key
            String base64Key = new String(Files.readAllBytes(Paths.get(keyFilePath))).trim();

            // 兼容 ENC(...) 格式
            if (encrypted.startsWith("ENC(") && encrypted.endsWith(")")) {
                encrypted = encrypted.substring(4, encrypted.length() - 1);
            }

            return AesUtil.decrypt(encrypted, base64Key);
        } catch (Exception e) {
            throw new RuntimeException("数据库密码解密失败", e);
        }
    }
}


