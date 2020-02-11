package com.otus.spring.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextXmlApplication {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("context.xml");
    }
}
