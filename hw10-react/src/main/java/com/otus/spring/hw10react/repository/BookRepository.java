package com.otus.spring.hw10react.repository;

import com.otus.spring.hw10react.domain.Author;
import com.otus.spring.hw10react.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    default List<Book> findByAuthorId(int authorId) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {
            final Join<Book, Author> join = root.join("authors");
            return criteriaBuilder.equal(join.get("id"), authorId);
        });
    }

    List<Book> findByGenreId(int genreId);
}
