package com.otus.spring.hw12authentication.exception;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(final int genreId) {
        super(String.format("Genre \"%d\" hasn't been found!", genreId));
    }
}
