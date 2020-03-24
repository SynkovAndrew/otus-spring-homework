package com.otus.spring.hw05jdbcdao.exception;

public class ReferencedObjectNotFoundException extends Exception {
    public ReferencedObjectNotFoundException() {
        super("Referenced object hasn't been found!");
    }
}
