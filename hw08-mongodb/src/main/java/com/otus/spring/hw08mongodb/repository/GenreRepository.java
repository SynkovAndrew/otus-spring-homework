package com.otus.spring.hw08mongodb.repository;

import com.otus.spring.hw08mongodb.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}
