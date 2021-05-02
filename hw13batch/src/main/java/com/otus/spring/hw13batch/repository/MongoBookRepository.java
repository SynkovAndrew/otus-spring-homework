package com.otus.spring.hw13batch.repository;

import com.otus.spring.hw13batch.domain.Author;
import com.otus.spring.hw13batch.domain.Book;
import com.otus.spring.hw13batch.domain.Genre;
import com.otus.spring.hw13batch.entity.BookMongoEntity;
import com.otus.spring.hw13batch.entity.BookSqlView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class MongoBookRepository implements BookRepository {
    private final MongoOperations mongoOperations;

    public List<Book> findBooks() {
        return mongoOperations.findAll(BookMongoEntity.class).stream()
                .map(entity ->
                        Book.builder()
                                .id(entity.getExternalId())
                                .year(entity.getYear())
                                .name(entity.getName())
                                .author(
                                        ofNullable(
                                                mongoOperations.findB(BookMongoEntity.class)
                                        )
                                )
                                .build()
                )
                .collect(Collectors.toList());
    }

    private Book map(BookSqlView view) {
        return Book.builder()
                .name(view.getName())
                .id(view.getId())
                .author(Author.builder()
                        .id(view.getAuthorId())
                        .name(view.getAuthorName())
                        .build())
                .genre(Genre.builder()
                        .id(view.getGenreId())
                        .name(view.getGenreName())
                        .build())
                .build();
    }
}
