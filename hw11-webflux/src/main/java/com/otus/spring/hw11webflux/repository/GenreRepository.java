package com.otus.spring.hw11webflux.repository;

import com.otus.spring.hw11webflux.domain.Genre;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
