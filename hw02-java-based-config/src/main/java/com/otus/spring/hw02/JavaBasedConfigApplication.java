package com.otus.spring.hw02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class JavaBasedConfigApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(JavaBasedConfigApplication.class);
    }
}
