package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    int create(Genre genre);

    int deleteById(int id);

    List<Genre> findAll(Options options);

    default List<Genre> findAll() {
        return findAll(Options.builder().build());
    }

    Optional<Genre> findById(int id, Options options);

    default Optional<Genre> findById(int id) {
        return findById(id, Options.builder().build());
    }

    int update(int id, Genre genre);
}
