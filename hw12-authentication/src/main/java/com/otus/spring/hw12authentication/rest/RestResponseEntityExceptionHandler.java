package com.otus.spring.hw12authentication.rest;

import com.otus.spring.hw12authentication.exception.AuthorNotFoundException;
import com.otus.spring.hw12authentication.exception.BookNotFoundException;
import com.otus.spring.hw12authentication.exception.CommentNotFoundException;
import com.otus.spring.hw12authentication.exception.GenreNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            BookNotFoundException.class,
            GenreNotFoundException.class,
            CommentNotFoundException.class,
            AuthorNotFoundException.class
    })
    protected ResponseEntity<Object> handleConflict(final Exception ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
