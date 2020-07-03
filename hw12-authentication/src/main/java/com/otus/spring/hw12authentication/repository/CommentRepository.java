package com.otus.spring.hw12authentication.repository;

import com.otus.spring.hw12authentication.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    void deleteByBookId(int bookId);

    Set<Comment> findAllByBookId(Integer bookId);
}
