package com.abelium.inatrace.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfiguration {
    
    @Bean
    public ConcurrentTaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    }
}
