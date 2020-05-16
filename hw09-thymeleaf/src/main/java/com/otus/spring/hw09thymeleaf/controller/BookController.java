package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw09thymeleaf.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/book")
    public ModelAndView create(final @ModelAttribute CreateOrUpdateBookRequestDTO request) {
        bookService.createOrUpdate(request);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/book/{bookId}/delete")
    public ModelAndView delete(final @PathVariable("bookId") int bookId) {
        bookService.deleteOne(bookId);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/book/{bookId}")
    public ModelAndView update(final @PathVariable("bookId") int bookId,
                               final @ModelAttribute CreateOrUpdateBookRequestDTO request) {
        request.setId(bookId);
        bookService.createOrUpdate(request);
        return new ModelAndView("redirect:/");
    }
}
