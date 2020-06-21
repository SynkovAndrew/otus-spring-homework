package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.FindAuthorsResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureWebTestClient
public class AuthorRequestsTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void findByIdTest() {
        webClient.get()
                .uri("/api/v1/author/{authorId}", "3")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDTO.class)
                .value(AuthorDTO::getName, equalTo("George Orwell"))
                .value(AuthorDTO::getId, equalTo("3"));
    }

    @Test
    public void findAllTest() {
        webClient.get()
                .uri("/api/v1/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FindAuthorsResponseDTO.class)
                .value(authors -> authors.getAuthors().size(), equalTo(4));
    }
}
