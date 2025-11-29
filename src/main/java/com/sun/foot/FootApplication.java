package com.sun.foot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sun.foot.mapper")
public class FootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootApplication.class, args);
    }

}
