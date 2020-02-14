package com.otus.spring.hw02;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JavaBasedConfigApplication {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("context.xml");
    }
}
