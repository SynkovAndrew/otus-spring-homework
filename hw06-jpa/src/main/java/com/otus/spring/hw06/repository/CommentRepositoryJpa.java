package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.*;

@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Comment> add(final Comment comment, final int bookId) {
        return ofNullable(entityManager.find(Book.class, bookId))
                .map(book -> {
                    book.getComments().add(comment);
                    entityManager.merge(book);
                    return of(comment);
                })
                .orElse(empty());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> find(final int commentId) {
        return ofNullable(entityManager.find(Comment.class, commentId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Comment> findAllByBookId(final int bookId) {
        return ofNullable(entityManager.find(Book.class, bookId))
                .map(Book::getComments)
                .orElse(Collections.emptySet());
    }

    @Override
    @Transactional
    public Optional<Comment> remove(final int bookId, final int commentId) {
        return find(commentId).map(comment -> {
            final var book = entityManager.find(Book.class, bookId);
            book.getComments().remove(comment);
            entityManager.remove(comment);
            return comment;
        });
    }
}
