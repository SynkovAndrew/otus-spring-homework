package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteById(final int id) {
        entityManager.createNativeQuery("delete from books_authors where book_id = :book_id")
                .setParameter("book_id", id)
                .executeUpdate();
        entityManager.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
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
    public Book save(final Book book) {
        return ofNullable(book.getId())
                .map(id -> entityManager.merge(book))
                .orElseGet(() -> {
                    entityManager.persist(book);
                    return book;
                });
    }

    @Override
    @Transactional
    public Optional<Book> update(final int id, final Book book) {
        return findById(id)
                .map(obj -> {
                    book.setId(id);
                    return save(book);
                });
    }
}
