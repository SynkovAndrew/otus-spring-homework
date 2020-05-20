package com.otus.spring.hw09thymeleaf.repository;

import com.otus.spring.hw09thymeleaf.domain.Author;
import com.otus.spring.hw09thymeleaf.dto.book.FindAuthorsRequestDTO;

import java.util.List;

public interface AuthorCustomRepository {
    List<Author> findAll(FindAuthorsRequestDTO request);
}
