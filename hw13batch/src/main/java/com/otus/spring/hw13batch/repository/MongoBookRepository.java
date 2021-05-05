package com.otus.spring.hw13batch.repository;

import com.otus.spring.hw13batch.domain.Author;
import com.otus.spring.hw13batch.domain.Book;
import com.otus.spring.hw13batch.domain.Genre;
import com.otus.spring.hw13batch.entity.mongo.AuthorMongoEntity;
import com.otus.spring.hw13batch.entity.mongo.BookMongoEntity;
import com.otus.spring.hw13batch.entity.mongo.GenreMongoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Component
@Qualifier("mongoBookRepository")
@RequiredArgsConstructor
public class MongoBookRepository implements BookRepository {
    private final MongoOperations ops;

    public List<Book> findBooks() {
        final Map<String, AuthorMongoEntity> authorMongoEntities = ops.findAll(AuthorMongoEntity.class).stream()
                .collect(toMap(AuthorMongoEntity::getId, Function.identity()));
        final Map<String, GenreMongoEntity> genreMongoEntities = ops.findAll(GenreMongoEntity.class).stream()
                .collect(toMap(GenreMongoEntity::getId, Function.identity()));
        return ops.findAll(BookMongoEntity.class).stream()
                .map(bookEntity -> map(bookEntity, authorMongoEntities, genreMongoEntities))
                .collect(Collectors.toList());
    }

    private Book map(BookMongoEntity bookEntity,
                     Map<String, AuthorMongoEntity> authorMongoEntities,
                     Map<String, GenreMongoEntity> genreMongoEntities) {
        return Book.builder()
                .id(Integer.valueOf(bookEntity.getId()))
                .year(bookEntity.getYear())
                .name(bookEntity.getName())
                .author(ofNullable(authorMongoEntities.get(bookEntity.getAuthorId()))
                        .map(authorMongoEntity -> Author.builder()
                                .id(Integer.valueOf(authorMongoEntity.getId()))
                                .name(authorMongoEntity.getName())
                                .build())
                        .orElse(null))
                .genre(ofNullable(genreMongoEntities.get(bookEntity.getAuthorId()))
                        .map(genreMongoEntity -> Genre.builder()
                                .id(Integer.valueOf(genreMongoEntity.getId()))
                                .name(genreMongoEntity.getName())
                                .build())
                        .orElse(null))
                .build();
    }
}
