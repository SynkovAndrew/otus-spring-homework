package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Comment;

import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> add(Comment comment, int bookId);

    Optional<Comment> find(int commentId);

    Optional<Comment> remove(int commentId);
}
