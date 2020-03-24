package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.domain.Genre;
import com.otus.spring.hw05jdbcdao.exception.ReferencedObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("Create new book")
    public void createTest() throws ReferencedObjectNotFoundException {
        final Book book = Book.builder()
                .name("Nineteen Eighty-Four")
                .author(Author.builder().id(3).build())
                .genre(Genre.builder().id(5).build())
                .year(1949)
                .build();

        bookDao.create(book);

        final List<Book> all = bookDao.findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").isNotNull();
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
    }

    @Test
    @DisplayName("Create new book with absent author")
    public void createWithAbsentAuthorTest() {
        final Book book = Book.builder()
                .name("Nineteen Eighty-Four")
                .author(Author.builder().id(134).build())
                .genre(Genre.builder().id(5).build())
                .year(1949)
                .build();

        assertThrows(ReferencedObjectNotFoundException.class, () -> bookDao.create(book));
    }

    @Test
    @DisplayName("Delete absent book")
    public void deleteAbsentTest() {
        final int result = bookDao.deleteById(123);
        assertThat(result).isEqualTo(0);

        final List<Book> all = bookDao.findAll();
        assertThat(all).size().isEqualTo(6);
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() {
        final int result = bookDao.deleteById(4);
        assertThat(result).isEqualTo(1);

        final List<Book> all = bookDao.findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea",
                        "Animal Farm", "The Psychopathology of Everyday Life");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1945, 1904);
    }

    @Test
    @DisplayName("Find absent book by id")
    public void findAbsentByIdTest() {
        final Optional<Book> optional = bookDao.findById(212);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find all books")
    public void findAllTest() {
        final List<Book> all = bookDao.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea",
                        "The Sun Also Rises", "Animal Farm", "The Psychopathology of Everyday Life");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1927, 1945, 1904);
        assertThat(all).extracting("author.id")
                .containsOnly(1, 1, 2, 2, 3, 4);
        assertThat(all).extracting("author.name")
                .containsOnly("Erich Maria Remarque", "Erich Maria Remarque", "Ernest Hemingway",
                        "Ernest Hemingway", "George Orwell", "Sigmund Freud");
        assertThat(all).extracting("genre.id")
                .containsOnly(6, 6, 6, 6, 5, 4);
        assertThat(all).extracting("genre.name")
                .containsOnly("Fiction", "Fiction", "Fiction", "Fiction", "Science Fiction", "Psychology");
    }

    @Test
    @DisplayName("Find book by id")
    public void findByIdTest() {
        final Book book = bookDao.findById(2).get();
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(2);
        assertThat(book).extracting("name").isEqualTo("The Black Obelisk");
        assertThat(book).extracting("author.name").isEqualTo("Erich Maria Remarque");
        assertThat(book).extracting("author.id").isEqualTo(1);
        assertThat(book).extracting("genre.name").isEqualTo("Fiction");
        assertThat(book).extracting("genre.id").isEqualTo(6);
    }

    @Test
    @DisplayName("Update absent book")
    public void updateAbsentTest() throws ReferencedObjectNotFoundException {
        final int result = bookDao.update(12, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .author(Author.builder()
                        .id(5)
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .build())
                .build());
        assertThat(result).isEqualTo(0);

        final Optional<Book> optional = bookDao.findById(12);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Update book")
    public void updateTest() throws ReferencedObjectNotFoundException {
        final int result = bookDao.update(4, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .author(Author.builder()
                        .id(5)
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .build())
                .build());
        assertThat(result).isEqualTo(1);

        final Book book = bookDao.findById(4).get();
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(4);
        assertThat(book).extracting("year").isEqualTo(1967);
        assertThat(book).extracting("name").isEqualTo("The Black Swan");
        assertThat(book).extracting("author.name").isEqualTo("Fedor Dostoevsky");
        assertThat(book).extracting("author.id").isEqualTo(5);
        assertThat(book).extracting("genre.name").isEqualTo("History");
        assertThat(book).extracting("genre.id").isEqualTo(2);
    }

    @Test
    @DisplayName("Update book with absent author")
    public void updateWithAbsentAuthorTest() {
        assertThrows(ReferencedObjectNotFoundException.class,
                () -> bookDao.update(4, Book.builder()
                        .name("The Black Swan")
                        .year(1235)
                        .author(Author.builder()
                                .id(1)
                                .build())
                        .genre(Genre.builder()
                                .id(154)
                                .build())
                        .build()));
    }
}
