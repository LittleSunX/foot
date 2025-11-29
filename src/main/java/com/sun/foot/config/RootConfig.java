package com.sun.foot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class RootConfig {

    /**
     * 定时任务异步线程池
     */
    @Bean(name = "asyncTaskPool")
    public ThreadPoolTaskExecutor asyncTaskPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 线程活跃时长
        executor.setKeepAliveSeconds(120);
        // 设置队列客容量
        executor.setQueueCapacity(10000);
        // 线程名称前缀
        executor.setThreadNamePrefix("AsyncTaskPool-");
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}
