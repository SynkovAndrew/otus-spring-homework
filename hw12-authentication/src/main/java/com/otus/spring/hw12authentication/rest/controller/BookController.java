package com.otus.spring.hw12authentication.rest.controller;

import com.otus.spring.hw12authentication.dto.book.BookDTO;
import com.otus.spring.hw12authentication.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw12authentication.exception.BookNotFoundException;
import com.otus.spring.hw12authentication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PutMapping("/api/v1/book/{bookId}/author/{authorId}/add")
    public BookDTO addAuthorToBook(final @PathVariable("bookId") int bookId,
                                   final @PathVariable("authorId") int authorId) throws BookNotFoundException {
        return bookService.addAuthor(bookId, authorId);
    }

    @PostMapping("/api/v1/book")
    public BookDTO create(final @RequestBody CreateOrUpdateBookRequestDTO request) {
        return bookService.create(request);
    }

    @PutMapping("/api/v1/book/{bookId}/author/{authorId}/delete")
    public BookDTO deleteAuthorFromBook(final @PathVariable("bookId") int bookId,
                                        final @PathVariable("authorId") int authorId) throws BookNotFoundException {
        return bookService.deleteAuthor(bookId, authorId);
    }

    @GetMapping("/api/v1/book/{bookId}")
    public BookDTO find(final @PathVariable("bookId") int bookId) throws BookNotFoundException {
        return bookService.find(bookId);
    }

    @GetMapping("/api/v1/books")
    public FindBooksResponseDTO findAll() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/author/{authorId}/books")
    public FindBooksResponseDTO findByAuthor(final @PathVariable("authorId") int authorId) {
        return bookService.findByGenre(authorId);
    }

    @GetMapping("/api/v1/genre/{genreId}/books")
    public FindBooksResponseDTO findByGenre(final @PathVariable("genreId") int genreId) {
        return bookService.findByGenre(genreId);
    }

    @PutMapping("/api/v1/book/{bookId}")
    public BookDTO update(final @PathVariable("bookId") int bookId,
                          final @RequestBody CreateOrUpdateBookRequestDTO request) throws BookNotFoundException {
        return bookService.update(bookId, request);
    }

    @DeleteMapping("/api/v1/book/{bookId}")
    public BookDTO delete(final @PathVariable("bookId") int bookId) throws BookNotFoundException {
        return bookService.delete(bookId);
    }
}
