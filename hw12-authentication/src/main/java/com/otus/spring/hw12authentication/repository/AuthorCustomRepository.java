package com.otus.spring.hw12authentication.repository;

import com.otus.spring.hw12authentication.domain.Author;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsRequestDTO;

import java.util.List;

public interface AuthorCustomRepository {
    List<Author> findAll(FindAuthorsRequestDTO request);
}
