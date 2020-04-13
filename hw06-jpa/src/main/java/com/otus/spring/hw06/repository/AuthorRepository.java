package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    void deleteById(int id);

    List<Author> findAll();

    Optional<Author> findById(int id);

    Author update(int id, Author author);
}
