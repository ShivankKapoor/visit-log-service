package com.shivankkapoor.visit_log_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class VisitLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitLogServiceApplication.class, args);
	}

}
