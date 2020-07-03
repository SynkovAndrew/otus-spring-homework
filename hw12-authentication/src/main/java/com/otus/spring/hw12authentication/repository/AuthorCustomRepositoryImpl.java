package com.otus.spring.hw12authentication.repository;

import com.otus.spring.hw12authentication.domain.Author;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class AuthorCustomRepositoryImpl implements AuthorCustomRepository {
    private final EntityManager entityManager;

    @Override
    public List<Author> findAll(final FindAuthorsRequestDTO request) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> author = criteriaQuery.from(Author.class);

        ofNullable(request.getAuthorIdNotIn())
                .ifPresent(authorIdNotIn -> criteriaQuery.where(
                        criteriaBuilder.not(author.get("id").in(authorIdNotIn))));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
