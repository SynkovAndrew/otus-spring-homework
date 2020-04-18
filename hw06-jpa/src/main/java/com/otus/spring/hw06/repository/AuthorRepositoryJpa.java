package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Author;
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
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteById(final int id) {
        final Query query = entityManager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        final TypedQuery<Author> query = entityManager.createQuery("select a from Author a ", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(final int id) {
        return ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    @Transactional
    public Author save(final Author author) {
        return ofNullable(author.getId())
                .map(id -> entityManager.merge(author))
                .orElseGet(() -> {
                    entityManager.persist(author);
                    return author;
                });
    }

    @Override
    @Transactional
    public Optional<Author> update(final int id, final Author author) {
        return findById(id)
                .map(obj -> {
                    author.setId(id);
                    return save(author);
                });
    }
}
