package com.otus.spring.hw08mongodb.repository;

import com.otus.spring.hw08mongodb.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Book> findByAuthor(final String authorId) {
        return mongoTemplate.find(Query.query(Criteria.where("authors.id").is(authorId)), Book.class);
    }
}
