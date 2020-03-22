package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Create new author")
    public void createTest() {
        final Author author = Author.builder().name("Lev Tolstoy").build();

        authorDao.create(author);

        final List<Author> all = authorDao.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").isNotNull();
        assertThat(all).extracting("name").containsOnlyOnce("Lev Tolstoy");
    }

    @Test
    @DisplayName("Delete author")
    public void deleteTest() {
        authorDao.deleteById(5);

        final List<Author> all = authorDao.findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4);
        assertThat(all).extracting("name")
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Find all authors")
    public void findAllTest() {
        final List<Author> all = authorDao.findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5);
        assertThat(all).extracting("name")
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell",
                        "Sigmund Freud", "Fedor Dostoevsky");
    }

    @Test
    @DisplayName("Find author by id")
    public void findByIdTest() {
        final Author author = authorDao.findById(3).get();
        assertThat(author).isNotNull();
        assertThat(author).extracting("id").isEqualTo(3);
        assertThat(author).extracting("name").isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Update author")
    public void updateTest() {
        authorDao.update(2, Author.builder().name("Nikolai Michailowitsch Karamsin").build());

        final Author author = authorDao.findById(2).get();
        assertThat(author).isNotNull();
        assertThat(author).extracting("id").isEqualTo(2);
        assertThat(author).extracting("name").isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
