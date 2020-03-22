package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    void deleteById(int id);

    List<Book> findAll();

    Optional<Book> findById(int id);

    void update(int id, Book book);
}
