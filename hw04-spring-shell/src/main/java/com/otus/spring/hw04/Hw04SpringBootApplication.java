package com.otus.spring.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.otus.spring.hw04.configuration")
public class Hw04SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw04SpringBootApplication.class, args);
    }

}
