package com.dmc.mam.aveco.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dmc.mam.aveco.model.DelayedFile;

@Configuration
@EnableTransactionManagement
@EnableScheduling
public class ApplicationConfig {

	@Bean("myQueue")
	public BlockingQueue<DelayedFile> getMyQueue() {
		return new DelayQueue<DelayedFile>();
	}
}
