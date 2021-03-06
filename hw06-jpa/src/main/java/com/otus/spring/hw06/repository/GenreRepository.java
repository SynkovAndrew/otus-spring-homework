package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> deleteById(int id);

    List<Genre> findAll();

    Optional<Genre> findById(int id);

    Genre save(Genre genre);

    Optional<Genre> update(int id, Genre genre);
}
