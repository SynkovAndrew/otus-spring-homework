package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.exception.ReferencedObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    int create(Book book) throws ReferencedObjectNotFoundException;

    int deleteById(int id);

    List<Book> findAll(Options options);

    default List<Book> findAll() {
        return findAll(Options.builder().build());
    }

    Optional<Book> findById(int id, Options options);

    default Optional<Book> findById(int id) {
        return findById(id, Options.builder().build());
    }

    int update(int id, Book book) throws ReferencedObjectNotFoundException;
}
