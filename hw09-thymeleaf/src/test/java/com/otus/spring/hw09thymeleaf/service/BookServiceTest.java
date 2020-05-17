package com.otus.spring.hw09thymeleaf.service;

import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw09thymeleaf.repository.AuthorRepository;
import com.otus.spring.hw09thymeleaf.repository.BookRepository;
import com.otus.spring.hw09thymeleaf.repository.CommentRepository;
import com.otus.spring.hw09thymeleaf.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Delete author from book")
    public void addAuthorTest() {
        service.addAuthor(4, 3);
        final Optional<BookDTO> book = service.findOne(4);
        assertThat(book).isPresent();
        assertThat(book).get().extracting("id").isEqualTo(4);
        assertThat(book.get().getAuthors()).extracting("name")
                .containsOnly("Erich Maria Remarque", "The Black Obelisk");
        assertThat(book.get().getAuthors()).extracting("id").containsOnly(2, 4);
    }

    @Test
    @DisplayName("Create new book")
    public void createTest() {
        final var book = CreateOrUpdateBookRequestDTO.builder()
                .name("Nineteen Eighty-Four")
                .authorIds(newHashSet(3))
                .genreId(5)
                .year(1949)
                .build();
        final var result = service.createOrUpdate(book);
        assertThat(result).isPresent();
        final List<BookDTO> all = service.findAll().getBooks();
        assertThat(all).size().isEqualTo(8);
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
        assertThat(all).extracting("year").containsOnlyOnce(1949);
    }

    @Test
    @DisplayName("Delete author from book")
    public void deleteAuthorTest() {
        service.deleteAuthor(4, 2);
        final Optional<BookDTO> book = service.findOne(4);
        assertThat(book).isPresent();
        assertThat(book).get().extracting("id").isEqualTo(4);
        assertThat(book.get().getAuthors()).isEmpty();
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() {
        final var result = service.deleteOne(4);
        assertThat(result).isPresent();
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
        assertThat(service.findOne(212)).isNotPresent();
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
    public void findByIdTest() {
        final Optional<BookDTO> book = service.findOne(2);
        assertThat(book).isPresent();
        assertThat(book).get().extracting("id").isEqualTo(2);
        assertThat(book).get().extracting("name").isEqualTo("The Black Obelisk");
        assertThat(book).get().extracting("genre.name").isEqualTo("Fiction");
        assertThat(book).get().extracting("genre.id").isEqualTo(6);
        assertThat(book.get().getAuthors()).isNotEmpty();
        assertThat(book.get().getAuthors()).extracting("name").containsOnly("Erich Maria Remarque");
        assertThat(book.get().getAuthors()).extracting("id").containsOnly(1);
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
    @DisplayName("Update book")
    public void updateTest() {
        final var result = service.createOrUpdate(CreateOrUpdateBookRequestDTO.builder()
                .name("The Black Swan")
                .id(4)
                .year(1967)
                .authorIds(newHashSet(1, 2))
                .genreId(2)
                .build());
        assertThat(result).isPresent();
        final Optional<BookDTO> book = service.findOne(4);
        assertThat(book).isPresent();
        assertThat(book).get().extracting("id").isEqualTo(4);
        assertThat(book).get().extracting("year").isEqualTo(1967);
        assertThat(book).get().extracting("name").isEqualTo("The Black Swan");
        assertThat(book.get().getAuthors()).isNotEmpty();
        assertThat(book.get().getAuthors()).extracting("name")
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway");
        assertThat(book.get().getAuthors()).extracting("id")
                .containsOnly(1, 2);
        assertThat(book).get().extracting("genre.name").isEqualTo("History");
        assertThat(book).get().extracting("genre.id").isEqualTo(2);
    }
}
