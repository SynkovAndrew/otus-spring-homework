package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.BookDTO;
import com.otus.spring.hw11webflux.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureWebTestClient
public class BookRequestsTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void findAllTest() {
        webClient.get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FindBooksResponseDTO.class)
                .value(response -> response.getBooks().size(), equalTo(7))
                .value(FindBooksResponseDTO::getBooks, hasItems(
                        BookDTO.builder()
                                .id("1")
                                .genre(GenreDTO.builder()
                                        .id("6")
                                        .name("Fiction")
                                        .build())
                                .name("The Night in Lisbon")
                                .year(1964)
                                .authors(Set.of(AuthorDTO.builder()
                                        .id("1")
                                        .name("Erich Maria Remarque")
                                        .build()))
                                .build(),
                        BookDTO.builder()
                                .id("2")
                                .genre(GenreDTO.builder()
                                        .id("6")
                                        .name("Fiction")
                                        .build())
                                .name("The Black Obelisk")
                                .year(1957)
                                .authors(Set.of(AuthorDTO.builder()
                                        .id("1")
                                        .name("Erich Maria Remarque")
                                        .build()))
                                .build()
                ));
    }

    @Test
    public void findByIdTest() {
        webClient.get()
                .uri("/api/v1/book/{bookId}", "2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDTO.class)
                .value(BookDTO::getName, equalTo("The Black Obelisk"))
                .value(BookDTO::getId, equalTo("2"))
                .value(
                        BookDTO::getGenre,
                        equalTo(GenreDTO.builder().id("6").name("Fiction").build())
                )
                .value(
                        BookDTO::getAuthors,
                        hasItem(AuthorDTO.builder().id("1").name("Erich Maria Remarque").build())
                );
    }
}
