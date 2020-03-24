package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.exception.ReferencedObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    int create(Book book) throws ReferencedObjectNotFoundException;

    int deleteById(int id);

    List<Book> findAll();

    Optional<Book> findById(int id);

    int update(int id, Book book) throws ReferencedObjectNotFoundException;
}
