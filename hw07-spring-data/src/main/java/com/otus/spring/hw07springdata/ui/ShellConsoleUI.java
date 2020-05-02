package com.otus.spring.hw07springdata.ui;

import com.otus.spring.hw07springdata.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw07springdata.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw07springdata.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw07springdata.service.AuthorService;
import com.otus.spring.hw07springdata.service.BookService;
import com.otus.spring.hw07springdata.service.CommentService;
import com.otus.spring.hw07springdata.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;


@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellConsoleUI extends ConsoleUI {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CommentService commentService;
    private final GenreService genreService;

    @ShellMethod(key = {"add comment", "ac"}, value = "add comment to book")
    public void addComment(final @ShellOption("bookId") int bookId,
                           final @ShellOption("comment") String comment) {
        final var request = AddCommentToBookRequestDTO.builder()
                .bookId(bookId)
                .comment(comment)
                .build();
        commentService.add(request)
                .ifPresentOrElse(
                        book -> show(() -> System.out.println("Comment has been added")),
                        () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", bookId))
                );
    }

    @ShellMethod(key = {"create book", "cb"}, value = "create new book")
    public void createBook(final @ShellOption("name") String name,
                           final @ShellOption("year") int year,
                           final @ShellOption("authorIds") int[] authorIds,
                           final @ShellOption("genreId") int genreId) {
        final var request = CreateOrUpdateBookRequestDTO.builder()
                .year(year)
                .name(name)
                .authorIds(IntStream.of(authorIds).boxed().collect(toSet()))
                .genreId(genreId)
                .build();
        bookService.createOrUpdate(request)
                .ifPresentOrElse(
                        book -> show(() -> System.out.println("Book has been created!")),
                        () -> show(() -> System.out.println("Failed to create book!"))
                );
    }

    @ShellMethod(key = {"find book comments", "fbc"}, value = "Find all comments for certain book")
    public void findBookComments(final @ShellOption("bookId") int bookId) {
        show(() -> commentService.findAllByBookId(bookId).forEach(System.out::println));
    }

    @ShellMethod(key = {"remove book", "rmb"}, value = "remove a book")
    public void removeBook(final @ShellOption("id") int id) {
        bookService.deleteOne(id)
                .ifPresentOrElse(
                        book -> show(() -> System.out.printf("Book \"%d\" has been removed!", book.getId())),
                        () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", id))
                );
    }

    @ShellMethod(key = {"remove comment", "rc"}, value = "add comment to book")
    public void removeComment(final @ShellOption("bookId") int bookId,
                              final @ShellOption("commentId") int commentId) {
        commentService.remove(RemoveCommentFromBookRequestDTO.builder().bookId(bookId).commentId(commentId).build())
                .ifPresentOrElse(
                        book -> show(() -> System.out.println("Comment has been removed")),
                        () -> show(() -> System.out.printf("Comment \"%d\" hasn't been found!", commentId))
                );
    }

    @ShellMethod(key = {"show authors", "sas"}, value = "show all authors")
    public void showAllAuthors() {
        show(() -> authorService.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show books", "sbs"}, value = "show all books")
    public void showAllBooks() {
        show(() -> bookService.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show books by author", "sbsba"}, value = "show all books by author")
    public void showAllBooksByAuthor(final @ShellOption("authorId") int authorId) {
        show(() -> bookService.findByAuthor(authorId).forEach(System.out::println));
    }

    @ShellMethod(key = {"show books by genre", "sbsbg"}, value = "show all books by genre")
    public void showAllBooksByGenre(final @ShellOption("genreId") int genreId) {
        show(() -> bookService.findByGenre(genreId).forEach(System.out::println));
    }

    @ShellMethod(key = {"show genres", "sgs"}, value = "show all genres")
    public void showAllGenres() {
        show(() -> genreService.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show book", "sb"}, value = "show a book")
    public void showBook(final @ShellOption("id") int id) {
        bookService.findOne(id).ifPresentOrElse(
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
        final var request = CreateOrUpdateBookRequestDTO.builder()
                .id(id)
                .year(year)
                .name(name)
                .authorIds(IntStream.of(authorIds).boxed().collect(toSet()))
                .genreId(genreId)
                .build();
        bookService.createOrUpdate(request)
                .ifPresentOrElse(
                        updated -> show(() -> System.out.printf("Book \"%d\" has been updated!", updated.getId())),
                        () -> show(() -> System.out.print("Book \"%d\" hasn't been found!"))
                );
    }
}
