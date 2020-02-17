package com.otus.spring.hw02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class JavaBasedConfigApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(JavaBasedConfigApplication.class);
    }
}
