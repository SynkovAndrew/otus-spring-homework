package com.otus.spring.hw06.repository;


import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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
    @Transactional
    public void remove(final int id) {
        entityManager.createQuery("delete from Comment c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
