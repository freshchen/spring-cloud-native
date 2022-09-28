package com.github.freshchen.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author darcy
 * @since 2022/09/23
 **/
@Configuration(proxyBeanMethods = false)
public class ThreadPollConfig {

    @Bean
    public Executor defaultExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setThreadNamePrefix("default-");
        taskExecutor.initialize();
        return taskExecutor;
    }

}
