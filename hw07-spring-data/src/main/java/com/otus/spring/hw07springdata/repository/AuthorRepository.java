package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Set<Author> findAllByIdIn(Set<Integer> authorIds);
}
