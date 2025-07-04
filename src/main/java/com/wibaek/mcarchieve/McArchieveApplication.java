package com.wibaek.mcarchieve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class McArchieveApplication {

    public static void main(String[] args) {
        SpringApplication.run(McArchieveApplication.class, args);
    }

}
