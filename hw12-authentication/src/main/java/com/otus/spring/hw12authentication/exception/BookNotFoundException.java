package com.otus.spring.hw12authentication.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(final int bookId) {
        super(String.format("Book \"%d\" hasn't been found!", bookId));
    }
}
