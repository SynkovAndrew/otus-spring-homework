package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.author.AuthorDTO;
import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
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

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerTest {
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
    public void addAuthorToBookTest() throws Exception {
        when(bookService.addAuthor(1, 2))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .name("Book")
                        .genre(GenreDTO.builder()
                                .id(3)
                                .name("Genre")
                                .build())
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());

        mockMvc.perform(put("/api/v1/book/1/author/2/add"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.genre.id", is(3)))
                .andExpect(jsonPath("$.genre.name", is("Genre")))
                .andExpect(jsonPath("$.authors[0].id", is(2)));
    }

    @Test
    public void addAuthorToAbsentBookTest() throws Exception {
        when(bookService.addAuthor(1234, 2))
                .thenThrow(new BookNotFoundException(1234));

        mockMvc.perform(put("/api/v1/book/1234/author/2/add"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Book \"1234\" hasn't been found!")));
    }

    @Test
    public void createTest() throws Exception {
        when(bookService.create(any(CreateOrUpdateBookRequestDTO.class)))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .name("Book")
                        .genre(GenreDTO.builder()
                                .id(3)
                                .name("Genre")
                                .build())
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());

        mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.genre.id", is(3)))
                .andExpect(jsonPath("$.genre.name", is("Genre")))
                .andExpect(jsonPath("$.authors[0].id", is(2)));
    }

    @Test
    public void deleteAuthorFromBookTest() throws Exception {
        when(bookService.deleteAuthor(1, 2))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .name("Book")
                        .genre(GenreDTO.builder()
                                .id(3)
                                .name("Genre")
                                .build())
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());

        mockMvc.perform(put("/api/v1/book/1/author/2/delete"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.genre.id", is(3)))
                .andExpect(jsonPath("$.genre.name", is("Genre")))
                .andExpect(jsonPath("$.authors[0].id", is(2)));
    }

    @Test
    public void deleteTest() throws Exception {
        when(bookService.delete(1))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .name("Book")
                        .genre(GenreDTO.builder()
                                .id(3)
                                .name("Genre")
                                .build())
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());
        mockMvc.perform(delete("/api/v1/book/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.genre.id", is(3)))
                .andExpect(jsonPath("$.genre.name", is("Genre")))
                .andExpect(jsonPath("$.authors[0].id", is(2)));
    }

    @Test
    public void updateTest() throws Exception {
        when(bookService.update(anyInt(), any(CreateOrUpdateBookRequestDTO.class)))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .name("Book")
                        .genre(GenreDTO.builder()
                                .id(3)
                                .name("Genre")
                                .build())
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());

        mockMvc.perform(put("/api/v1/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Book")))
                .andExpect(jsonPath("$.genre.id", is(3)))
                .andExpect(jsonPath("$.genre.name", is("Genre")))
                .andExpect(jsonPath("$.authors[0].id", is(2)));
    }
}
