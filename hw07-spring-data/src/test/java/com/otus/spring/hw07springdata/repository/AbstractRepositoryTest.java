package com.otus.spring.hw07springdata.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

public abstract class AbstractRepositoryTest<T> {
    protected final TestEntityManager entityManager;
    private final String className;
    private final Class<T> clazz;

    protected AbstractRepositoryTest(final Class<T> clazz, final TestEntityManager entityManager) {
        this.clazz = clazz;
        this.entityManager = entityManager;
        this.className = clazz.getSimpleName();
    }

    protected List<T> findAll() {
        final var simpleName = clazz.getSimpleName();
        return entityManager.getEntityManager()
                .createQuery(String.format("select a from %s a", className), clazz)
                .getResultList();
    }

    protected T findOne(final int id) {
        return entityManager.getEntityManager().find(clazz, id);
    }
}
