package com.example.inbound.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class InboundProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InboundProcessingApplication.class, args);
	}

}
