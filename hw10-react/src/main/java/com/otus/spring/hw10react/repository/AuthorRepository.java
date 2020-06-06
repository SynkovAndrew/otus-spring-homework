package com.otus.spring.hw10react.repository;

import com.otus.spring.hw10react.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, AuthorCustomRepository {
    Set<Author> findAllByIdIn(Set<Integer> authorIds);
}
