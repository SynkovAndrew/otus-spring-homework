package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import com.otus.spring.hw11webflux.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureWebTestClient
public class CommentRequestsTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private WebTestClient webClient;

    @Test
    public void createTest() {
        webClient.post()
                .uri("/api/v1/comment")
                .body(Mono.just(
                        AddCommentToBookRequestDTO.builder()
                                .bookId("6")
                                .comment("Great book! I have read it dozen of times")
                                .build()
                        ),
                        AddCommentToBookRequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDTO.class)
                .value(CommentDTO::getId, notNullValue())
                .value(CommentDTO::getValue, equalTo("Great book! I have read it dozen of times"));

        StepVerifier.create(commentRepository.findAll().collectList())
                .expectNextMatches(comments -> comments.stream()
                        .map(Comment::getValue)
                        .collect(toSet())
                        .contains("Great book! I have read it dozen of times")
                );
    }
}
