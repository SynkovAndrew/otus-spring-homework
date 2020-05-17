package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.genre.GenreDTO;
import com.otus.spring.hw09thymeleaf.service.AuthorService;
import com.otus.spring.hw09thymeleaf.service.BookService;
import com.otus.spring.hw09thymeleaf.service.CommentService;
import com.otus.spring.hw09thymeleaf.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ViewControllerTest {
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private GenreService genreService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void newBookTest() throws Exception {
        when(genreService.findAll())
                .thenReturn(List.of(
                        GenreDTO.builder()
                                .id(1)
                                .name("A")
                                .build(),
                        GenreDTO.builder()
                                .id(2)
                                .name("B")
                                .build()
                ));
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("genres", List.of(
                        GenreDTO.builder()
                                .id(1)
                                .name("A")
                                .build(),
                        GenreDTO.builder()
                                .id(2)
                                .name("B")
                                .build()
                )))
                .andExpect(model()
                        .attribute("saveBookRequest", CreateOrUpdateBookRequestDTO.builder().build()))
                .andExpect(model()
                        .attribute("book", BookDTO.builder().build()));
    }

    @Test
    public void editBookTest() throws Exception {
        when(genreService.findAll())
                .thenReturn(List.of(
                        GenreDTO.builder()
                                .id(1)
                                .name("A")
                                .build(),
                        GenreDTO.builder()
                                .id(2)
                                .name("B")
                                .build()
                ));
        mockMvc.perform(get("/book/23"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("genres", List.of(
                        GenreDTO.builder()
                                .id(1)
                                .name("A")
                                .build(),
                        GenreDTO.builder()
                                .id(2)
                                .name("B")
                                .build()
                )))
                .andExpect(model()
                        .attribute("saveBookRequest", CreateOrUpdateBookRequestDTO.builder().build()))
                .andExpect(model()
                        .attribute("book", BookDTO.builder().id(23).build()));
    }
}
