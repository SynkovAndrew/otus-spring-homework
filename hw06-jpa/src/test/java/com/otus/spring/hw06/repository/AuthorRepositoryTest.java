package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("Delete absent author")
    public void deleteAbsentTest() {
        repository.deleteById(12);

        final List<Author> all = findAll();
        assertThat(all).size().isEqualTo(4);
    }

    @Test
    @DisplayName("Delete author")
    public void deleteTest() {
        repository.deleteById(4);

        final List<Author> all = findAll();
        assertThat(all).size().isEqualTo(3);
        assertThat(all).extracting(Author::getId).containsOnly(1, 2, 3);
        assertThat(all).extracting(Author::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell");
    }

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        final Optional<Author> optional = repository.findById(11);
        assertThat(optional).isNotPresent();
    }

    private List<Author> findAll() {
        return entityManager.getEntityManager()
                .createQuery("select a from Author a ", Author.class)
                .getResultList();
    }

    @Test
    @DisplayName("Find all authors")
    public void findAllTest() {
        final List<Author> all = repository.findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting(Author::getId).containsOnly(1, 2, 3, 4);
        assertThat(all).extracting(Author::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Find author by id")
    public void findByIdTest() {
        final Optional<Author> author = repository.findById(3);
        assertThat(author).get().isNotNull();
        assertThat(author).get().extracting(Author::getId).isEqualTo(3);
        assertThat(author).get().extracting(Author::getName).isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Save new author")
    public void saveTest() {
        final Author author = Author.builder().name("Lev Tolstoy").build();

        repository.save(author);

        final List<Author> all = findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting(Author::getId).isNotNull();
        assertThat(all).extracting(Author::getName).containsOnlyOnce("Lev Tolstoy");
    }

    @Test
    @DisplayName("Update absent author")
    public void updateAbsentTest() {
        repository.update(11, Author.builder().name("Nikolai Michailowitsch Karamsin").build());

        final Optional<Author> optional = repository.findById(11);
        assertThat(optional).isNotPresent();
    }

    @Test
    @DisplayName("Update author")
    public void updateTest() {
        repository.update(1, Author.builder().name("Nikolai Michailowitsch Karamsin").build());

        final Optional<Author> author = repository.findById(1);
        assertThat(author).isPresent();
        assertThat(author).get().extracting(Author::getId).isEqualTo(1);
        assertThat(author).get().extracting(Author::getName).isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
