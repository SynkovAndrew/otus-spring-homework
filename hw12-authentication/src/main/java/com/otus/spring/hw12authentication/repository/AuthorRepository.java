package com.otus.spring.hw12authentication.repository;

import com.otus.spring.hw12authentication.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, AuthorCustomRepository {
    Set<Author> findAllByIdIn(Set<Integer> authorIds);
}
