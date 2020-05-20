package com.otus.spring.hw10react.controller;

import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public BookDTO create(final @RequestBody CreateOrUpdateBookRequestDTO request) {
        return bookService.create(request);
    }

    @GetMapping("/api/v1/book/{bookId}")
    public BookDTO findOne(final @PathVariable("bookId") int bookId) throws BookNotFoundException {
        return bookService.findOne(bookId);
    }

    @PutMapping("/api/v1/book/{bookId}")
    public BookDTO update(final @PathVariable("bookId") int bookId,
                          final @RequestBody CreateOrUpdateBookRequestDTO request) throws BookNotFoundException {
        return bookService.update(bookId, request);
    }
}
