package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Genre;

import java.util.Optional;

public interface GenreDao {
    void create(Genre genre);

    void deleteById(int id);

    Optional<Genre> findById(int id);

    void update(int id, Genre genre);
}
