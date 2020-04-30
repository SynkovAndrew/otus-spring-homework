package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> deleteById(int id);

    List<Author> findAll();

    Optional<Author> findById(int id);

    Author save(Author author);

    Optional<Author> update(int id, Author author);
}
