package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Set<Comment> findAllByBookId(Integer bookId);
}
