package com.otus.spring.hw09thymeleaf.repository;

import com.otus.spring.hw09thymeleaf.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Set<Comment> findAllByBookId(Integer bookId);
}
