package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    int create(Book book);

    int deleteById(int id);

    List<Book> findAll();

    Optional<Book> findById(int id);

    int update(int id, Book book);
}
