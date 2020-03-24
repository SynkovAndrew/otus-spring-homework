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
    @DisplayName("Delete absent author")
    public void deleteAbsentTest() {
        final int result = authorDao.deleteById(12);
        assertThat(result).isEqualTo(0);

        final List<Author> all = authorDao.findAll();
        assertThat(all).size().isEqualTo(5);
    }

    @Test
    @DisplayName("Delete author")
    public void deleteTest() {
        final int result = authorDao.deleteById(5);
        assertThat(result).isEqualTo(1);

        final List<Author> all = authorDao.findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4);
        assertThat(all).extracting("name")
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        final Optional<Author> optional = authorDao.findById(11);
        assertThat(optional.isEmpty()).isTrue();
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
        assertThat(author.getBooks()).extracting("name")
                .containsOnly("Animal Farm");
        assertThat(author.getBooks()).extracting("year")
                .containsOnly(1945);
    }

    @Test
    @DisplayName("Update absent author")
    public void updateAbsentTest() {
        final int result = authorDao.update(11, Author.builder().name("Nikolai Michailowitsch Karamsin").build());
        assertThat(result).isEqualTo(0);
        final Optional<Author> optional = authorDao.findById(11);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Update author")
    public void updateTest() {
        final int result = authorDao.update(1, Author.builder().name("Nikolai Michailowitsch Karamsin").build());
        assertThat(result).isEqualTo(1);
        final Author author = authorDao.findById(1).get();
        assertThat(author).isNotNull();
        assertThat(author).extracting("id").isEqualTo(1);
        assertThat(author).extracting("name").isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
