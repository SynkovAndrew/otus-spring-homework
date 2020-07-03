package com.otus.spring.hw12authentication.exception;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(final int authorId) {
        super(String.format("Author \"%d\" hasn't been found!", authorId));
    }
}
