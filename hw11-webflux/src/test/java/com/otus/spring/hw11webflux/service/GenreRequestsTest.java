package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureWebTestClient
public class GenreRequestsTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void findAllTest() {
        webClient.get()
                .uri("/api/v1/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FindGenresResponseDTO.class)
                .value(response -> response.getGenres().size(), equalTo(6));
    }

    @Test
    public void findByIdTest() {
        webClient.get()
                .uri("/api/v1/genre/{genreId}", "2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDTO.class)
                .value(GenreDTO::getName, equalTo("History"))
                .value(GenreDTO::getId, equalTo("2"));
    }
}
