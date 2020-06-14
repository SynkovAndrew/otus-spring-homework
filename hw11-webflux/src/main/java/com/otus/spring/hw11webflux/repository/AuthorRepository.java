package com.otus.spring.hw11webflux.repository;

import com.otus.spring.hw11webflux.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Set;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findAllByIdIn(Set<String> authorIds);
}
