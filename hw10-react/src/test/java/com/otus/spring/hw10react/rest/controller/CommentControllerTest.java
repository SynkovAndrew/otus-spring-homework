package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.service.AuthorService;
import com.otus.spring.hw10react.service.BookService;
import com.otus.spring.hw10react.service.CommentService;
import com.otus.spring.hw10react.service.GenreService;
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
    public void addComment() throws Exception {
        mockMvc.perform(post("/book/4/comment/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("bookId", "12"),
                        new BasicNameValuePair("value", "test comment")
                )))))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/book/4/comments"));
    }

    @Test
    public void deleteComment() throws Exception {
        mockMvc.perform(post("/book/3/comment/7/delete"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/book/3/comments"));
    }
}
