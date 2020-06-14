package com.otus.spring.hw11webflux.repository;

import com.otus.spring.hw11webflux.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Book> findByAuthor(final String authorId) {
        return mongoTemplate.find(Query.query(Criteria.where("authors.id").is(authorId)), Book.class);
    }
}
