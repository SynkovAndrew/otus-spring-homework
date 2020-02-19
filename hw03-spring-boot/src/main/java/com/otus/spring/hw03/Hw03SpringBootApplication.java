package com.otus.spring.hw03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.otus.spring.hw03.configuration")
public class Hw03SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw03SpringBootApplication.class, args);
    }

}
