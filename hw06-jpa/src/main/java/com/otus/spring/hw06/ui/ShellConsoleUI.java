package com.otus.spring.hw06.ui;

import com.otus.spring.hw06.domain.Author;
import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Comment;
import com.otus.spring.hw06.domain.Genre;
import com.otus.spring.hw06.repository.AuthorRepository;
import com.otus.spring.hw06.repository.BookRepository;
import com.otus.spring.hw06.repository.CommentRepository;
import com.otus.spring.hw06.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Collections;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;


@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellConsoleUI extends ConsoleUI {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    @ShellMethod(key = {"add comment", "ac"}, value = "add comment to book")
    public void addComment(final @ShellOption("bookId") int bookId,
                           final @ShellOption("comment") String comment) {
        final var obj = Comment.builder().value(comment).build();
        commentRepository.add(obj, bookId)
                .ifPresentOrElse(
                        book -> show(() -> System.out.println("Comment has been added")),
                        () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", bookId))
                );
    }

    @ShellMethod(key = {"remove comment", "rc"}, value = "add comment to book")
    public void addComment(final @ShellOption("commentId") int commentId) {
        commentRepository.remove(commentId);
        show(() -> System.out.println("Comment has been removed"));
    }


    @ShellMethod(key = {"create book", "cb"}, value = "create new book")
    public void createBook(final @ShellOption("name") String name,
                           final @ShellOption("year") int year,
                           final @ShellOption("authorIds") int[] authorIds,
                           final @ShellOption("genreId") int genreId) {
        final var book = Book.builder()
                .year(year)
                .name(name)
                .authors(IntStream.of(authorIds)
                        .mapToObj(authorId -> Author.builder().id(authorId).build())
                        .collect(toSet()))
                .genre(Genre.builder().id(genreId).build())
                .build();
        bookRepository.save(book);
        show(() -> System.out.print("Book has been created!"));
    }

    @ShellMethod(key = {"remove book", "rmb"}, value = "remove a book")
    public void removeBook(final @ShellOption("id") int id) {
        bookRepository.deleteById(id)
                .ifPresentOrElse(
                        book -> show(() -> System.out.printf("Book \"%d\" has been removed!", book.getId())),
                        () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", id))
                );
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
                           final @ShellOption("authorIds") int[] authorIds,
                           final @ShellOption("genreId") int genreId) {
        final var book = Book.builder()
                .year(year)
                .name(name)
                .authors(IntStream.of(authorIds)
                        .mapToObj(authorId -> Author.builder().id(authorId).build())
                        .collect(toSet()))
                .comments(Collections.emptySet())
                .genre(Genre.builder().id(genreId).build())
                .build();
        bookRepository.update(id, book)
                .ifPresentOrElse(
                        updated -> show(() -> System.out.printf("Book \"%d\" has been updated!", updated.getId())),
                        () -> show(() -> System.out.print("Book \"%d\" hasn't been found!"))
                );
    }
}
