package com.otus.spring.hw06.ui;

import com.otus.spring.hw06.domain.Author;
import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Genre;
import com.otus.spring.hw06.repository.AuthorRepository;
import com.otus.spring.hw06.repository.BookRepository;
import com.otus.spring.hw06.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static com.google.common.collect.Sets.newHashSet;


@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellUI {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @ShellMethod(key = {"create book", "cb"}, value = "create new book")
    public void createBook(final @ShellOption("name") String name,
                           final @ShellOption("year") int year,
                           final @ShellOption("authorId") int authorId,
                           final @ShellOption("genreId") int genreId) {
        bookRepository.save(Book.builder()
                .year(year)
                .name(name)
                .authors(newHashSet(Author.builder().id(authorId).build()))
                .genre(Genre.builder().id(genreId).build())
                .build());
        show(() -> System.out.print("Book has been created!"));

/*        try {

        } catch (ReferencedObjectNotFoundException e) {
            show(() -> System.out.print("Failed to create book! Referenced author or genre hasn't been found!"));
        }*/
    }

    @ShellMethod(key = {"remove book", "rmb"}, value = "remove a book")
    public void removeBook(final @ShellOption("id") int id) {
        bookRepository.deleteById(id)
                .ifPresentOrElse(
                        book -> show(() -> System.out.printf("Book \"%d\" has been removed!", book.getId())),
                        () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", id))
                );
    }

    private void show(final Runnable runnable) {
        System.out.println();
        runnable.run();
        System.out.println();
    }

    @ShellMethod(key = {"show authors", "sas"}, value = "show all authors")
    public void showAllAuthors() {
        show(() -> authorRepository.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show books", "sbs"}, value = "show all books")
    public void showAllBooks() {
        show(() -> bookRepository.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show genres", "sgs"}, value = "show all genres")
    public void showAllGenres() {
        show(() -> genreRepository.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show book", "sb"}, value = "show a book")
    public void showBook(final @ShellOption("id") int id) {
        bookRepository.findById(id).ifPresentOrElse(
                book -> show(() -> System.out.println(book)),
                () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", id))
        );
    }

    @ShellMethod(key = {"update book", "ub"}, value = "update a book")
    public void updateBook(final @ShellOption("id") int id,
                           final @ShellOption("name") String name,
                           final @ShellOption("year") int year,
                           final @ShellOption("authorId") int authorId,
                           final @ShellOption("genreId") int genreId) {
        bookRepository.update(id, Book.builder()
                .year(year)
                .name(name)
                .authors(newHashSet(Author.builder().id(authorId).build()))
                .genre(Genre.builder().id(genreId).build())
                .build());
        show(() -> System.out.printf("Book \"%d\" has been updated!", id));
/*        try {

        } catch (ReferencedObjectNotFoundException e) {
            show(() -> System.out.print("Failed to update book! Referenced author or genre hasn't been found!"));
        }*/
    }
}
