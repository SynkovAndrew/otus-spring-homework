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
public class BookRepositoryTest extends AbstractDataJpaTest<Book> {
    private final BookRepository repository;

    @Autowired
    protected BookRepositoryTest(TestEntityManager entityManager,
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
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").isNotNull();
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
    }

    @Test
    @DisplayName("Create new book with absent author")
    public void createWithAbsentAuthorTest() {
        final Book book = Book.builder()
                .name("Nineteen Eighty-Four")
                .authors(newHashSet(Author.builder().id(134).build()))
                .genre(Genre.builder().id(5).build())
                .year(1949)
                .build();
        repository.save(book);

        final List<Book> all = findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").isNotNull();
        assertThat(all).extracting("name").containsOnlyOnce("Nineteen Eighty-Four");
    }

    @Test
    @DisplayName("Delete absent book")
    public void deleteAbsentTest() {
        repository.deleteById(123);

        final List<Book> all = repository.findAll();
        assertThat(all).size().isEqualTo(6);
    }

    @Test
    @DisplayName("Delete book")
    public void deleteTest() {
        repository.deleteById(4);

        final List<Book> all = repository.findAll();
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
        assertThat(repository.findById(212)).isNotPresent();
    }

    @Test
    @DisplayName("Find all books")
    public void findAllTest() {
        final List<Book> all = repository.findAll();
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
        final Optional<Book> book = repository.findById(2);
        assertThat(book).isPresent();
        assertThat(book).get().extracting("id").isEqualTo(2);
        assertThat(book).get().extracting("name").isEqualTo("The Black Obelisk");
        assertThat(book).get().extracting("author.name").isEqualTo("Erich Maria Remarque");
        assertThat(book).get().extracting("author.id").isEqualTo(1);
        assertThat(book).get().extracting("genre.name").isEqualTo("Fiction");
        assertThat(book).get().extracting("genre.id").isEqualTo(6);
    }

    @Test
    @DisplayName("Update absent book")
    public void updateAbsentTest() {
        repository.update(12, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .authors(newHashSet(Author.builder().id(5).build()))
                .genre(Genre.builder().id(2).build())
                .build());

        final Optional<Book> optional = repository.findById(12);
        assertThat(optional).isNotPresent();
    }

    @Test
    @DisplayName("Update book")
    public void updateTest() {
        repository.update(4, Book.builder()
                .name("The Black Swan")
                .year(1967)
                .authors(newHashSet(Author.builder().id(5).build()))
                .genre(Genre.builder().id(2).build())
                .build());

        final Optional<Book> book = repository.findById(4);
        assertThat(book).isNotNull();
        assertThat(book).get().extracting("id").isEqualTo(4);
        assertThat(book).get().extracting("year").isEqualTo(1967);
        assertThat(book).get().extracting("name").isEqualTo("The Black Swan");
        assertThat(book).get().extracting("author.name").isEqualTo("Fedor Dostoevsky");
        assertThat(book).get().extracting("author.id").isEqualTo(5);
        assertThat(book).get().extracting("genre.name").isEqualTo("History");
        assertThat(book).get().extracting("genre.id").isEqualTo(2);
    }

    @Test
    @DisplayName("Update book with absent author")
    public void updateWithAbsentAuthorTest() {
        assertThrows(Exception.class,
                () -> repository.update(4, Book.builder()
                        .name("The Black Swan")
                        .year(1235)
                        .authors(newHashSet(Author.builder().id(1).build()))
                        .genre(Genre.builder().id(154).build())
                        .build()));
    }
}
