package com.otus.spring.hw09thymeleaf.repository;

import com.otus.spring.hw09thymeleaf.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
