package com.otus.spring.hw12authentication.exception;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException(final int commentId) {
        super(String.format("Comment \"%d\" hasn't been found!", commentId));
    }
}
