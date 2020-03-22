package com.otus.spring.hw05jdbcdao.ui;

import com.otus.spring.hw05jdbcdao.dao.BookDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellUI {
    private final BookDao bookDao;

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

    @ShellMethod(key = {"show books", "sbs"}, value = "show all books")
    public void showAllBooks() {
        show(() -> bookDao.findAll().forEach(System.out::println));
    }

    @ShellMethod(key = {"show book", "sb"}, value = "show a book")
    public void showBook(final @ShellOption("id") int id) {
        bookDao.findById(id).ifPresentOrElse(
                book -> show(() -> System.out.println(book)),
                () -> show(() -> System.out.printf("Book \"%d\" hasn't been found!", id))
        );
    }
}
