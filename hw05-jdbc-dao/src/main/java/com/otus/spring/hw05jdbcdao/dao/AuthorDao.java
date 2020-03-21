package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    void deleteById(int id);

    List<Author> findAll();

    Optional<Author> findById(int id);

    void update(int id, Author author);
}
