package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.repository.AuthorRepository;
import com.otus.spring.hw10react.repository.BookRepository;
import com.otus.spring.hw10react.repository.CommentRepository;
import com.otus.spring.hw10react.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;

@DataJpaTest
public class BookServiceTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GenreRepository genreRepository;
    private BookService service;

    @Test
    @DisplayName("Add author to book")
    public void addAuthorTest() throws BookNotFoundException {
        final var book = service.addAuthor(4, 3);
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(4);
        assertThat(book.getAuthors()).extracting("name")
                .containsOnly("Ernest Hemingway", "George Orwell");
        assertThat(book.getAuthors()).extracting("id").containsOnly(2, 3);
    }

    @Test
    @DisplayName("Add author to absent book")
    public void addAuthorToAbsentBookTest() {
        assertThrows(BookNotFoundException.class, () -> service.addAuthor(123, 3));
    }

    @Test
    @DisplayName("Create new book")
    public void createTest() {
        final var request = CreateOrUpdateBookRequestDTO.builder()
                .name("Nineteen Eighty-Four")
                .authorIds(newHashSet(3))
                .genreId(5)
                .year(1949)
                .build();
        final var result = service.create(request);
        assertThat(result).isNotNull();
        final List<BookDTO> all = service.findAll().getBooks();
        assertThat(all).size().isEqualTo(8);
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
        assertThat(all).extracting("year").containsOnlyOnce(1949);
    }

    @Test
    @DisplayName("Delete absent book")
    public void deleteAbsentBookTest() {
        assertThrows(BookNotFoundException.class, () -> service.delete(123));
    }

    @Test
    @DisplayName("Delete author from book")
    public void deleteAuthorTest() throws BookNotFoundException {
        service.deleteAuthor(4, 2);
        final BookDTO book = service.find(4);
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(4);
        assertThat(book.getAuthors()).isEmpty();
    }

    @Test
    @DisplayName("Delete author from absent book")
    public void deleteAuthorToAbsentBookTest() {
        assertThrows(BookNotFoundException.class, () -> service.deleteAuthor(123, 3));
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() throws BookNotFoundException {
        final var result = service.delete(4);
        assertThat(result).isNotNull();
        final List<BookDTO> all = service.findAll().getBooks();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 5, 6, 7);
        assertThat(all).extracting("name")
                .containsOnly("The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea",
                        "Animal Farm", "The Psychopathology of Everyday Life", "The Authorless Book");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1945, 1904, 1974);
    }

    @Test
    @DisplayName("Find absent book by id")
    public void findAbsentByIdTest() {
        assertThrows(BookNotFoundException.class, () -> service.find(212));
    }

    @Test
    @DisplayName("Find all books")
    public void findAllTest() {
        final List<BookDTO> all = service.findAll().getBooks();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6, 7);
        assertThat(all).extracting("name")
                .containsOnly("The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea", "The Sun Also Rises",
                        "Animal Farm", "The Psychopathology of Everyday Life", "The Authorless Book");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1927, 1945, 1904, 1974);
        assertThat(all).extracting("genre.id")
                .containsOnly(6, 6, 6, 6, 5, 4);
        assertThat(all).extracting("genre.name")
                .containsOnly("Fiction", "Fiction", "Fiction", "Fiction", "Science Fiction", "Psychology");
    }

    @Test
    @DisplayName("Find books by genre")
    public void findBooksByAuthorTest() {
        final List<BookDTO> all = service.findByAuthor(1).getBooks();
        assertThat(all).size().isEqualTo(2);
        assertThat(all).extracting("id").containsOnly(1, 2);
        assertThat(all).extracting("name").containsOnly(
                "The Night in Lisbon", "The Black Obelisk");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957);
        assertThat(all).extracting("genre.id")
                .containsOnly(6, 6);
        assertThat(all).extracting("genre.name")
                .containsOnly("Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find books by genre")
    public void findBooksByGenreTest() {
        final List<BookDTO> all = service.findByGenre(6).getBooks();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4);
        assertThat(all).extracting("name").containsOnly(
                "The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea", "The Sun Also Rises");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1927);
        assertThat(all).extracting("genre.id")
                .containsOnly(6, 6, 6, 6);
        assertThat(all).extracting("genre.name")
                .containsOnly("Fiction", "Fiction", "Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find book by id")
    public void findByIdTest() throws BookNotFoundException {
        final BookDTO book = service.find(2);
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(2);
        assertThat(book).extracting("name").isEqualTo("The Black Obelisk");
        assertThat(book).extracting("genre.name").isEqualTo("Fiction");
        assertThat(book).extracting("genre.id").isEqualTo(6);
        assertThat(book.getAuthors()).isNotEmpty();
        assertThat(book.getAuthors()).extracting("name").containsOnly("Erich Maria Remarque");
        assertThat(book.getAuthors()).extracting("id").containsOnly(1);
    }

    @BeforeEach
    public void setUp() {
        service = new BookService(
                authorRepository,
                bookRepository,
                commentRepository,
                genreRepository,
                getMapper(MappingService.class)
        );
    }

    @Test
    @DisplayName("Update absent book by id")
    public void updateAbsentByIdTest() {
        assertThrows(BookNotFoundException.class,
                () -> service.update(2345, CreateOrUpdateBookRequestDTO.builder()
                        .name("The Black Swan")
                        .year(1967)
                        .authorIds(newHashSet(1, 2))
                        .genreId(2)
                        .build())
        );
    }

    @Test
    @DisplayName("Update book")
    public void updateTest() throws BookNotFoundException {
        final BookDTO book = service.update(4, CreateOrUpdateBookRequestDTO.builder()
                .name("The Black Swan")
                .year(1967)
                .authorIds(newHashSet(1, 2))
                .genreId(2)
                .build());
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(4);
        assertThat(book).extracting("year").isEqualTo(1967);
        assertThat(book).extracting("name").isEqualTo("The Black Swan");
        assertThat(book.getAuthors()).isNotEmpty();
        assertThat(book.getAuthors()).extracting("name")
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway");
        assertThat(book.getAuthors()).extracting("id")
                .containsOnly(1, 2);
        assertThat(book).extracting("genre.name").isEqualTo("History");
        assertThat(book).extracting("genre.id").isEqualTo(2);
    }
}
