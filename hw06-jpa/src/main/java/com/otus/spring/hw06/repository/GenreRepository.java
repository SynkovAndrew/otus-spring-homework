package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    void deleteById(int id);

    List<Genre> findAll();

    Optional<Genre> findById(int id);

    Genre save(Genre genre);

    Genre update(int id, Genre genre);
}
