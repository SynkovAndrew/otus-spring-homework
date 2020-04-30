package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
