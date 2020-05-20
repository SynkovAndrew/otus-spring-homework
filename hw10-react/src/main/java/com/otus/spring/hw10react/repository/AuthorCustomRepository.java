package com.otus.spring.hw10react.repository;

import com.otus.spring.hw10react.domain.Author;
import com.otus.spring.hw10react.dto.book.FindAuthorsRequestDTO;

import java.util.List;

public interface AuthorCustomRepository {
    List<Author> findAll(FindAuthorsRequestDTO request);
}
