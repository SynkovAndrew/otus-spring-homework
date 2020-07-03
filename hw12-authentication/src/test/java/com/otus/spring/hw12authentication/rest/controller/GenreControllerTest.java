package com.otus.spring.hw12authentication.rest.controller;

import com.otus.spring.hw12authentication.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw12authentication.dto.genre.GenreDTO;
import com.otus.spring.hw12authentication.service.AuthorService;
import com.otus.spring.hw12authentication.service.BookService;
import com.otus.spring.hw12authentication.service.CommentService;
import com.otus.spring.hw12authentication.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GenreControllerTest {
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
    public void findAllTest() throws Exception {
        when(genreService.findAll()).thenReturn(
                FindGenresResponseDTO.builder()
                        .genres(List.of(
                                GenreDTO.builder()
                                        .id(1)
                                        .name("Genre 1")
                                        .build(),
                                GenreDTO.builder()
                                        .id(2)
                                        .name("Genre 2")
                                        .build()
                        ))
                        .build()
        );

        mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.genres[0].id", is(1)))
                .andExpect(jsonPath("$.genres[0].name", is("Genre 1")))
                .andExpect(jsonPath("$.genres[1].id", is(2)))
                .andExpect(jsonPath("$.genres[1].name", is("Genre 2")));
    }

}
