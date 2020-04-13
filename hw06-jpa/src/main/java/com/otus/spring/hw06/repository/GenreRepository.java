package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    int create(Genre genre);

    int deleteById(int id);

    List<Genre> findAll();

    Optional<Genre> findById(int id);

    int update(int id, Genre genre);
}
