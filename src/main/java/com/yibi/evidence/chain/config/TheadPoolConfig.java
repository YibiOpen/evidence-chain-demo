package com.yibi.evidence.chain.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *线程池配置
 *@author yibi
 *@date 2021-05-19
 */
@Configuration
public class TheadPoolConfig {

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(4,
                new BasicThreadFactory.Builder().namingPattern("evi-pool-%d").daemon(true).build());
    }
}
