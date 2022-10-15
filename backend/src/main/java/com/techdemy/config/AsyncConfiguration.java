package com.techdemy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean
    public Executor taskExecutor(){
        log.debug("Creating Async task executor");
        final ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(2);
        threadPoolExecutor.setMaxPoolSize(2);
        threadPoolExecutor.setQueueCapacity(100);
        threadPoolExecutor.setThreadNamePrefix("TechDemyThread-");
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }

}
