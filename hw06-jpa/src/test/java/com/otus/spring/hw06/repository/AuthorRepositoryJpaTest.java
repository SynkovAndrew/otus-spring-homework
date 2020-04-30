package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Author;
import com.otus.spring.hw06.domain.Genre;
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
public class AuthorRepositoryJpaTest extends AbstractDataJpaTest<Author> {
    private final AuthorRepository repository;

    @Autowired
    protected AuthorRepositoryJpaTest(TestEntityManager entityManager,
                                      AuthorRepository repository) {
        super(Author.class, entityManager);
        this.repository = repository;
    }

    @Test
    @DisplayName("Delete absent author")
    public void deleteAbsentTest() {
        final var author = repository.deleteById(12);
        assertThat(author).isNotPresent();
        final List<Author> all = findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting(Author::getId).containsOnly(1, 2, 3, 4);
        assertThat(all).extracting(Author::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Delete author")
    public void deleteTest() {
        final var author = repository.deleteById(4);
        assertThat(author).isPresent();
        final List<Author> all = findAll();
        assertThat(all).size().isEqualTo(3);
        assertThat(all).extracting(Author::getId).containsOnly(1, 2, 3);
        assertThat(all).extracting(Author::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell");
    }

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        final Author author = findOne(11);
        assertThat(author).isNull();
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
        final Author author = findOne(3);
        assertThat(author).isNotNull();
        assertThat(author).extracting(Author::getId).isEqualTo(3);
        assertThat(author).extracting(Author::getName).isEqualTo("George Orwell");
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
        final var all = findAll();
        assertThat(all).extracting("name").doesNotContain("Nikolai Michailowitsch Karamsin");
    }

    @Test
    @DisplayName("Update author")
    public void updateTest() {
        repository.update(1, Author.builder().name("Nikolai Michailowitsch Karamsin").build());
        final Author author = findOne(1);
        assertThat(author).isNotNull();
        assertThat(author).extracting(Author::getId).isEqualTo(1);
        assertThat(author).extracting(Author::getName).isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
