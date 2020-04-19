package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> deleteById(int id);

    List<Book> findAll();

    Optional<Book> findById(int id);

    Optional<Book> save(Book book);

    Optional<Book> update(int id, Book book);
}
