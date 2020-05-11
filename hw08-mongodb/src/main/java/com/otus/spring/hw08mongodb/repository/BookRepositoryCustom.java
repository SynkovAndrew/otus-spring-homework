package com.otus.spring.hw08mongodb.repository;

import com.otus.spring.hw08mongodb.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findByAuthor(String authorId);
}
