package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.author.AuthorDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw10react.service.AuthorService;
import com.otus.spring.hw10react.service.BookService;
import com.otus.spring.hw10react.service.CommentService;
import com.otus.spring.hw10react.service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuthorControllerTest {
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
        when(authorService.findAll(any(FindAuthorsRequestDTO.class))).thenReturn(
                FindAuthorsResponseDTO.builder()
                        .authors(List.of(
                                AuthorDTO.builder()
                                        .id(1)
                                        .name("Author 1")
                                        .build(),
                                AuthorDTO.builder()
                                        .id(2)
                                        .name("Author 2")
                                        .build()
                        ))
                        .build()
        );

        final var captor = ArgumentCaptor.forClass(FindAuthorsRequestDTO.class);

        mockMvc.perform(get("/api/v1/authors")
                .param("authorIdNotIn", "3", "4"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.authors[0].id", is(1)))
                .andExpect(jsonPath("$.authors[0].name", is("Author 1")))
                .andExpect(jsonPath("$.authors[1].id", is(2)))
                .andExpect(jsonPath("$.authors[1].name", is("Author 2")));

        verify(authorService, times(1))
                .findAll(captor.capture());
        final var request = captor.getValue();
        assertThat(request).isNotNull();
        assertThat(request.getAuthorIdNotIn()).isNotEmpty();
        assertThat(request.getAuthorIdNotIn()).containsExactly(3, 4);
    }

}
