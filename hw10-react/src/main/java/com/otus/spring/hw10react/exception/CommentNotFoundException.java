package com.otus.spring.hw10react.exception;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException(final int commentId) {
        super(String.format("Comment \"%d\" hasn't been found!", commentId));
    }
}
