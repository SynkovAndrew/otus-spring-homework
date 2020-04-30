package com.otus.spring.hw07springdata.repository;


import com.otus.spring.hw07springdata.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        final Optional<Author> author = repository.findById(11);
        assertThat(author).isNotPresent();
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
        assertThat(author).isPresent();
        assertThat(author).get().extracting(Author::getId).isEqualTo(3);
        assertThat(author).get().extracting(Author::getName).isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Save new author")
    public void saveTest() {
        final Author author = Author.builder().name("Lev Tolstoy").build();
        repository.save(author);
        final List<Author> all = repository.findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting(Author::getId).isNotNull();
        assertThat(all).extracting(Author::getName).containsOnlyOnce("Lev Tolstoy");
    }


    @Test
    @DisplayName("Update author")
    public void updateTest() {
        repository.save(Author.builder().name("Nikolai Michailowitsch Karamsin").id(1).build());
        final Author author = repository.getOne(1);
        assertThat(author).isNotNull();
        assertThat(author).extracting(Author::getId).isEqualTo(1);
        assertThat(author).extracting(Author::getName).isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
