package com.otus.spring.hw08mongodb.repository;

import com.otus.spring.hw08mongodb.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
    Set<Author> findAllByIdIn(Set<String> authorIds);
}
