package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    int create(Book book);

    int deleteById(int id);

    List<Book> findAll();

    Optional<Book> findById(int id);

    int update(int id, Book book);
}