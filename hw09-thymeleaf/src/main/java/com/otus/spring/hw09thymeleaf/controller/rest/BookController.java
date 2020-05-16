package com.otus.spring.hw09thymeleaf.controller.rest;

import com.otus.spring.hw09thymeleaf.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw09thymeleaf.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("api/v1/books")
    public FindBooksResponseDTO find() {
        return bookService.findAll();
    }
}
