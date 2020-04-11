package com.otus.spring.hw05jdbcdao.ui;

import com.otus.spring.hw05jdbcdao.dao.AuthorDao;
import com.otus.spring.hw05jdbcdao.dao.BookDao;
import com.otus.spring.hw05jdbcdao.dao.GenreDao;
import com.otus.spring.hw05jdbcdao.domain.Author;
import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.domain.Genre;
import com.otus.spring.hw05jdbcdao.exception.ReferencedObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellUI {
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    @ShellMethod(key = {"create book", "cb"}, value = "create new book")
    public void createBook(final @ShellOption("name") String name,
                           final @ShellOption("year") int year,
                           final @ShellOption("authorId") int authorId,
                           final @ShellOption("genreId") int genreId) {
        try {
            bookDao.create(Book.builder()
                    .year(year)
                    .name(name)
                    .author(Author.builder().id(authorId).build())
                    .genre(Genre.builder().id(genreId).build())
                    .build());
            show(() -> System.out.print("Book has been created!"));
        } catch (ReferencedObjectNotFoundException e) {
            show(() -> System.out.print("Failed to create book! Referenced author or genre hasn't been found!"));
        }
    }

    @ShellMethod(key = {"remove book", "rmb"}, value = "remove a book")
    public void removeBook(final @ShellOption("id") int id) {
        final int result = bookDao.deleteById(id);
        if (result == 0) {
            show(() -> System.out.printf("Book \"%d\" hasn't been found!", id));
        } else {
            show(() -> System.out.printf("Book \"%d\" has been removed!", id));
        }
    }

    private void show(final Runnable runnable) {
        System.out.println();
        runnable.run();
        System.out.println();
    }

    @ShellMethod(key = {"show authors", "sas"}, value = "show all authors")
    public void showAllAuthors() {
        show(() -> authorDao.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show books", "sbs"}, value = "show all books")
    public void showAllBooks() {
        show(() -> bookDao.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show genres", "sgs"}, value = "show all genres")
    public void showAllGenres() {
        show(() -> genreDao.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show book", "sb"}, value = "show a book")
    public void showBook(final @ShellOption("id") int id) {
        bookDao.findById(id).ifPresentOrElse(
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
        try {
            bookDao.update(id, Book.builder()
                    .year(year)
                    .name(name)
                    .author(Author.builder().id(authorId).build())
                    .genre(Genre.builder().id(genreId).build())
                    .build());
            show(() -> System.out.printf("Book \"%d\" has been updated!", id));
        } catch (ReferencedObjectNotFoundException e) {
            show(() -> System.out.print("Failed to update book! Referenced author or genre hasn't been found!"));
        }
    }
}
