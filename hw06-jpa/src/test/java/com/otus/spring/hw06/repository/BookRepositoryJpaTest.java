package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Author;
import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest extends AbstractDataJpaTest<Book> {
    private final BookRepository repository;

    @Autowired
    protected BookRepositoryJpaTest(TestEntityManager entityManager,
                                    BookRepository repository) {
        super(Book.class, entityManager);
        this.repository = repository;
    }

    @Test
    @DisplayName("Create new book")
    public void createTest() {
        final Book book = Book.builder()
                .name("Nineteen Eighty-Four")
                .authors(newHashSet(Author.builder().id(3).build()))
                .genre(Genre.builder().id(5).build())
                .year(1949)
                .build();

        repository.save(book);

        final List<Book> all = findAll();
        assertThat(all).size().isEqualTo(8);
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
        assertThat(all).extracting("year").containsOnlyOnce(1949);
    }

    @Test
    @DisplayName("Delete absent book")
    public void deleteAbsentTest() {
        final var book = repository.deleteById(123);
        assertThat(book).isNotPresent();
        final List<Book> all = repository.findAll();
        assertThat(all).size().isEqualTo(7);
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() {
        final var book = repository.deleteById(4);
        assertThat(book).isPresent();

        final List<Book> all = repository.findAll();
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
        assertThat(repository.findById(212)).isNotPresent();
    }

    @Test
    @DisplayName("Find all books")
    public void findAllTest() {
        final List<Book> all = repository.findAll();
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
        final Optional<Book> book = repository.findById(2);
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

    @Test
    @DisplayName("Update absent book")
    public void updateAbsentTest() {
        final var optional = repository.update(12, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .authors(newHashSet(Author.builder().id(5).build()))
                .genre(Genre.builder().id(2).build())
                .build());
        assertThat(optional).isNotPresent();
        final var all = findAll();
        assertThat(all).extracting("name").doesNotContain("Nikolai Michailowitsch Karamsin");
    }

    @Test
    @DisplayName("Update book")
    public void updateTest() {
        repository.update(4, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .authors(newHashSet(Author.builder().id(1).build(), Author.builder().id(2).build()))
                .genre(Genre.builder().id(2).build())
                .build());
        final Book book = findOne(4);
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

    @Test
    @DisplayName("Update book with absent reference")
    public void updateTest_absentAuthor() {
        final var optional = repository.update(4, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .authors(newHashSet(Author.builder().id(111).build()))
                .genre(Genre.builder().id(2).build())
                .build());
        assertThat(optional).isNotPresent();
        entityManager.getEntityManager().getTransaction().rollback();

        final Book book = findOne(4);
        assertThat(book).isNotNull();
        assertThat(book).extracting("id").isEqualTo(4);
        assertThat(book).extracting("year").isEqualTo(1927);
        assertThat(book).extracting("name").isEqualTo("The Sun Also Rises");
        assertThat(book.getAuthors()).isNotEmpty();
        assertThat(book.getAuthors()).extracting("name")
                .containsOnly("Ernest Hemingway");
        assertThat(book.getAuthors()).extracting("id")
                .containsOnly(2);
        assertThat(book).extracting("genre.name").isEqualTo("Fiction");
        assertThat(book).extracting("genre.id").isEqualTo(6);
    }
}
