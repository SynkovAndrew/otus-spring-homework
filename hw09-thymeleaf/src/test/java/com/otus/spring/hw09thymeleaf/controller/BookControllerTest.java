package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.service.AuthorService;
import com.otus.spring.hw09thymeleaf.service.BookService;
import com.otus.spring.hw09thymeleaf.service.CommentService;
import com.otus.spring.hw09thymeleaf.service.GenreService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    public void addAuthor() throws Exception {
        mockMvc.perform(post("/book/11/add/author")
                .param("authorId", "12"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/book/11"));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(post("/book/3/delete"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void deleteAuthor() throws Exception {
        mockMvc.perform(post("/book/2/delete/author/4"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/book/2"));
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
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
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
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
    }
}
