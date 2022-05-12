package com.example.pilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PilotApplication {
	public static void main(String[] args) {
		SpringApplication.run(PilotApplication.class, args);
	}
}