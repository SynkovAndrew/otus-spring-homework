package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
