package com.otus.spring.hw10react.repository;

import com.otus.spring.hw10react.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    void deleteByBookId(int bookId);

    Set<Comment> findAllByBookId(Integer bookId);
}
