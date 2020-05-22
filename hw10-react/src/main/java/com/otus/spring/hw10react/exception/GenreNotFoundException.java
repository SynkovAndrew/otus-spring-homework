package com.otus.spring.hw10react.exception;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(final int genreId) {
        super(String.format("Genre \"%d\" hasn't been found!", genreId));
    }
}
