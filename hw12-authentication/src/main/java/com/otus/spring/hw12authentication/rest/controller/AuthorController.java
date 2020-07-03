package com.otus.spring.hw12authentication.rest.controller;

import com.otus.spring.hw12authentication.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw12authentication.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/v1/authors")
    public FindAuthorsResponseDTO findAll(
            final @RequestParam(name = "authorIdNotIn", required = false) int[] authorIdNotIn) {
        return authorService.findAll(FindAuthorsRequestDTO.builder()
                .authorIdNotIn(ofNullable(authorIdNotIn)
                        .map(ids -> IntStream.of(ids)
                                .boxed()
                                .collect(toSet()))
                        .orElse(null))
                .build());
    }

    @GetMapping("/api/v1/book/{bookId}/authors")
    public FindAuthorsResponseDTO findBookAuthors(final @PathVariable("bookId") int bookId) {
        return authorService.findBookAuthors(bookId);
    }
}
