package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.service.BookService;
import com.otus.spring.hw09thymeleaf.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final BookService bookService;
    private final GenreService genreService;

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("books", bookService.findAll().getBooks());
        return "index";
    }

    @GetMapping("/book/{id}")
    public String index(final @PathVariable("id") Integer id, final Model model) {
        final var book = bookService.findOne(id)
                .orElse(BookDTO.builder()
                        .build());
        final var genres = genreService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        return "book";
    }
}
