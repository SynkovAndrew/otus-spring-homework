package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Genre;
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
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Genre> deleteById(final int id) {
        return findById(id).map(genre -> {
            entityManager.remove(genre);
            return genre;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        final TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g ", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(final int id) {
        return ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    @Transactional
    public Genre save(final Genre genre) {
        return ofNullable(genre.getId())
                .map(id -> entityManager.merge(genre))
                .orElseGet(() -> {
                    entityManager.persist(genre);
                    return genre;
                });
    }

    @Override
    @Transactional
    public Optional<Genre> update(final int id, final Genre genre) {
        return findById(id)
                .map(obj -> {
                    genre.setId(id);
                    return save(genre);
                });
    }
}
