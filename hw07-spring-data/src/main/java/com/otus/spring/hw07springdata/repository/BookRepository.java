package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
