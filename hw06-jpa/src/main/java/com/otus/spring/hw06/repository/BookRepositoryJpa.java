package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.*;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Book> deleteById(final int id) {
        return findById(id).map(book -> {
            entityManager.createNativeQuery("delete from books_authors where book_id = :book_id")
                    .setParameter("book_id", id)
                    .executeUpdate();
            entityManager.remove(book);
            return book;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        final TypedQuery<Book> query = entityManager.createQuery("select b from Book b ", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(final int id) {
        return ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    @Transactional
    public Optional<Book> save(final Book book) {
        try {
            if (nonNull(book.getId())) {
                entityManager.merge(book);
                return of(book);
            }
            entityManager.persist(book);
            return of(book);
        } catch (EntityNotFoundException e) {
            return empty();
        }
    }

    @Override
    @Transactional
    public Optional<Book> update(final int id, final Book book) {
        return findById(id)
                .flatMap(obj -> {
                    book.setId(id);
                    return save(book);
                });
    }
}
