package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {
    @Override
    public int create(Genre genre) {
        return 0;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public Optional<Genre> findById(int id) {
        return Optional.empty();
    }

    @Override
    public int update(int id, Genre genre) {
        return 0;
    }
}
