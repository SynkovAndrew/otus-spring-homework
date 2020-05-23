package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw10react.dto.comment.CommentDTO;
import com.otus.spring.hw10react.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.exception.CommentNotFoundException;
import com.otus.spring.hw10react.service.AuthorService;
import com.otus.spring.hw10react.service.BookService;
import com.otus.spring.hw10react.service.CommentService;
import com.otus.spring.hw10react.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CommentControllerTest {
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
    public void addCommentToAbsentBookTest() throws Exception {
        when(commentService.add(any(AddCommentToBookRequestDTO.class)))
                .thenThrow(new BookNotFoundException(456));

        mockMvc.perform(post("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Book \"456\" hasn't been found!")));
    }

    @Test
    public void deleteAbsentCommentTest() throws Exception {
        when(commentService.remove(any(RemoveCommentFromBookRequestDTO.class)))
                .thenThrow(new CommentNotFoundException(123));

        mockMvc.perform(delete("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Comment \"123\" hasn't been found!")));
    }

    @Test
    public void addCommentTest() throws Exception {
        when(commentService.add(any(AddCommentToBookRequestDTO.class)))
                .thenReturn(CommentDTO.builder()
                        .id(1)
                        .value("This it comment!")
                        .build());

        mockMvc.perform(post("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.value", is("This it comment!")));
    }

    @Test
    public void deleteCommentTest() throws Exception {
        when(commentService.remove(any(RemoveCommentFromBookRequestDTO.class)))
                .thenReturn(CommentDTO.builder()
                        .id(1)
                        .value("This it comment!")
                        .build());

        mockMvc.perform(delete("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.value", is("This it comment!")));
    }
}
