package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    int create(Author author);

    int deleteById(int id);

    List<Author> findAll(Options options);

    default List<Author> findAll() {
        return findAll(Options.builder().build());
    }

    Optional<Author> findById(int id, Options options);

    default Optional<Author> findById(int id) {
        return findById(id, Options.builder().build());
    }

    int update(int id, Author author);
}
