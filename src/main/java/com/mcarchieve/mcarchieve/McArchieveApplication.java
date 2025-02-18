package com.mcarchieve.mcarchieve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class McArchieveApplication {

	public static void main(String[] args) {
		SpringApplication.run(McArchieveApplication.class, args);
	}

}
