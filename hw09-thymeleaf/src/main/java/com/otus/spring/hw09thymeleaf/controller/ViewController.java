package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.genre.GenreDTO;
import com.otus.spring.hw09thymeleaf.service.BookService;
import com.otus.spring.hw09thymeleaf.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static java.util.Optional.ofNullable;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final BookService bookService;
    private final GenreService genreService;

    @GetMapping("/book")
    public String book(final Model model) {
        final var genres = genreService.findAll();
        model.addAttribute("book", BookDTO.builder().build());
        model.addAttribute("saveBookRequest",  CreateOrUpdateBookRequestDTO.builder().build());
        model.addAttribute("genres", genres);
        return "book";
    }

    @GetMapping("/book/{id}")
    public String book(final @PathVariable("id") Integer id, final Model model) {
        final var book = bookService.findOne(id)
                .orElse(BookDTO.builder()
                        .build());
        final var request = CreateOrUpdateBookRequestDTO.builder()
                .genreId(ofNullable(book.getGenre())
                        .map(GenreDTO::getId)
                        .orElse(null))
                .name(book.getName())
                .year(book.getYear())
                .id(book.getId())
                .build();
        final var genres = genreService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("saveBookRequest", request);
        model.addAttribute("genres", genres);
        return "book";
    }

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("books", bookService.findAll().getBooks());
        return "index";
    }
}
