package com.otus.spring.hw13batch.repository;

import com.otus.spring.hw13batch.domain.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findBooks();
}
