package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw10react.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/api/v1/authors")
    public FindAuthorsResponseDTO findAll(final @RequestBody FindAuthorsRequestDTO request) {
        return authorService.findAll(request);
    }

    @GetMapping("/api/v1/book/{bookId}/authors")
    public FindAuthorsResponseDTO findBookAuthors(final @PathVariable("bookId") int bookId) {
        return authorService.findBookAuthors(bookId);
    }
}
