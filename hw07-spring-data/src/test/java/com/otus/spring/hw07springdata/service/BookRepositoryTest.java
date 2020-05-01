package com.otus.spring.hw07springdata.service;

import com.otus.spring.hw07springdata.dto.book.BookDTO;
import com.otus.spring.hw07springdata.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw07springdata.repository.AuthorRepository;
import com.otus.spring.hw07springdata.repository.BookRepository;
import com.otus.spring.hw07springdata.repository.GenreRepository;
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
public class BookRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    private BookService service;

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
        final List<BookDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(8);
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
        assertThat(all).extracting("year").containsOnlyOnce(1949);
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() {
        final var result = service.deleteOne(4);
        assertThat(result).isPresent();
        final List<BookDTO> all = service.findAll();
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
        final List<BookDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6, 7);
        assertThat(all).extracting("name")
                .containsOnly("The Night in Lisbon", "The Black Obelisk", "The Old Man and the Sea", "The Sun Also Rises",
                        "Animal Farm", "The Psychopathology of Everyday Life", "The Authorless Book");
        assertThat(all).extracting("year")
                .containsOnly(1964, 1957, 1951, 1927, 1945, 1904, 1974);
        assertThat(all).flatExtracting("authors").isNotEmpty();
        assertThat(all).flatExtracting("authors").extracting("name")
                .containsOnly("Erich Maria Remarque", "Erich Maria Remarque", "Ernest Hemingway",
                        "Ernest Hemingway", "George Orwell", "Sigmund Freud");
        assertThat(all).flatExtracting("authors").extracting("id")
                .containsOnly(1, 1, 2, 2, 3, 4);
        assertThat(all).flatExtracting("comments").extracting("id")
                .containsOnly(1, 2, 3, 4, 5, 6, 7);
        assertThat(all).flatExtracting("comments").extracting("value")
                .containsOnly("Interesting book. Hope everybody will enjoy it!",
                        "The greatest I ever read!",
                        "The good one. I have advised it to my father",
                        "Good book!",
                        "I dont really like it, coudnt even finish it...",
                        "I love this book. I have re-read it 3 times already!",
                        "It is difficult one but worth to read it!");
        assertThat(all).extracting("genre.id")
                .containsOnly(6, 6, 6, 6, 5, 4);
        assertThat(all).extracting("genre.name")
                .containsOnly("Fiction", "Fiction", "Fiction", "Fiction", "Science Fiction", "Psychology");
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
        assertThat(book.get().getComments()).isNotEmpty();
        assertThat(book.get().getComments()).extracting("id").containsOnly(3);
        assertThat(book.get().getComments()).extracting("value").containsOnly("Good book!");
    }

    @BeforeEach
    public void setUp() {
        service = new BookService(authorRepository, bookRepository, genreRepository, getMapper(MappingService.class));
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
