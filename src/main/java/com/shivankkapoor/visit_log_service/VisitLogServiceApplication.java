package com.shivankkapoor.visit_log_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class VisitLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitLogServiceApplication.class, args);
	}

}
