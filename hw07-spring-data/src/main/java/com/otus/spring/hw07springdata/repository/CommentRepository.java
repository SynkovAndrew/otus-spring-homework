package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}