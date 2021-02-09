package com.genui.statistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan("com.genui.statistics")
public class ThreadPoolTaskSchedulerConfig {
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		
		scheduler.setPoolSize(5);
		scheduler.setThreadNamePrefix("ThreadSchedular");
		
		return scheduler;
	}
}