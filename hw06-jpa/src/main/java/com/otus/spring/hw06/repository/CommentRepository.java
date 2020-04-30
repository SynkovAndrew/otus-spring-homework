package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Comment;

import java.util.Optional;
import java.util.Set;

public interface CommentRepository {
    Optional<Comment> add(Comment comment, int bookId);

    Optional<Comment> find(int commentId);

    Set<Comment> findAllByBookId(int bookId);

    Optional<Comment> remove(int bookId, int commentId);
}
