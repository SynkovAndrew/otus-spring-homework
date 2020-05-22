package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.author.AuthorDTO;
import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.service.AuthorService;
import com.otus.spring.hw10react.service.BookService;
import com.otus.spring.hw10react.service.CommentService;
import com.otus.spring.hw10react.service.GenreService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void addAuthorToBook() throws Exception {
        when(bookService.addAuthor(1, 2))
                .thenReturn(BookDTO.builder()
                        .id(1)
                        .authors(Set.of(AuthorDTO.builder()
                                .id(2)
                                .build()))
                        .build());

        mockMvc.perform(put("/api/v1/book/1/author/2/add"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.authors.id", Matchers.containsInAnyOrder(2)));
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("genreId", "12"),
                        new BasicNameValuePair("name", "Andrey"),
                        new BasicNameValuePair("year", "123")
                )))))
                .andExpect(status().is(302));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(post("/book/3/delete"))
                .andExpect(status().is(302));
    }

    @Test
    public void deleteAuthor() throws Exception {
        mockMvc.perform(post("/book/2/delete/author/4"))
                .andExpect(status().is(302));
    }

    @Test
    public void update() throws Exception {
        mockMvc.perform(post("/book/23")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("genreId", "12"),
                        new BasicNameValuePair("name", "Andrey"),
                        new BasicNameValuePair("year", "123")
                )))))
                .andExpect(status().is(302));
    }
}
