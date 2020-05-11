package com.otus.spring.hw08mongodb.service;


import com.otus.spring.hw08mongodb.configuration.AbstractMongoDataLoader;
import com.otus.spring.hw08mongodb.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw08mongodb.dto.comment.CommentDTO;
import com.otus.spring.hw08mongodb.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw08mongodb.repository.BookRepository;
import com.otus.spring.hw08mongodb.repository.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

@DataMongoTest
public class CommentServiceDataLoader extends AbstractMongoDataLoader {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    private CommentService commentService;

    @Autowired
    public CommentServiceDataLoader(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Test
    @DisplayName("Add comment to book")
    public void addCommentTest() {
        final var request1 = AddCommentToBookRequestDTO.builder()
                .bookId("4")
                .comment("What a beautiful thing!!!")
                .build();
        final var request2 = AddCommentToBookRequestDTO.builder()
                .bookId("4")
                .comment("The best!!!")
                .build();
        final var result1 = commentService.add(request1);
        final var result2 = commentService.add(request2);
        assertThat(result1).isPresent();
        assertThat(result2).isPresent();
        final var book = bookRepository.findById("4").get();
        assertThat(book).isNotNull();
        assertThat(book.getComments()).isNotNull();
        assertThat(book.getComments()).hasSize(2);
        assertThat(book.getComments()).extracting("id").doesNotContainNull();
        assertThat(book.getComments()).extracting("value")
                .containsOnly("What a beautiful thing!!!", "The best!!!");
    }

    @Test
    @DisplayName("Add comment to absent book")
    public void addCommentTest_bookNotFound() {
        final var request = AddCommentToBookRequestDTO.builder()
                .bookId("400")
                .comment("What a beautiful thing!!!")
                .build();
        assertThat(commentService.add(request)).isNotPresent();
    }

    @Test
    @DisplayName("Find comment of book")
    public void findCommentTest() {
        final var comment = commentService.find("3");
        assertThat(comment).isPresent();
        assertThat(comment).get().extracting(CommentDTO::getId).isEqualTo("3");
        assertThat(comment).get().extracting(CommentDTO::getValue).isEqualTo("Good book!");
    }

    @Test
    @DisplayName("Find all comments for certain book")
    public void findCommentsByBookId() {
        final var comments = commentService.findAllByBookId("3");
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(CommentDTO::getValue).containsOnly(
                "The good one. I have advised it to my father", "I dont really like it, coudnt even finish it...");
    }

    @Test
    @DisplayName("Remove comment")
    public void removeCommentTest() {
        final var response = commentService.remove(RemoveCommentFromBookRequestDTO.builder()
                .bookId("1")
                .commentId("1")
                .build());
        assertThat(response).isPresent();
        final var book = bookRepository.findById("1").get();
        assertThat(book).isNotNull();
        assertThat(book.getComments()).hasSize(1);
        assertThat(book.getComments()).extracting("id").doesNotContainNull();
        assertThat(book.getComments()).extracting("value")
                .containsOnly("The greatest I ever read!");
    }

    @BeforeEach
    public void setUp() {
        commentService = new CommentService(bookRepository, commentRepository, getMapper(MappingService.class));
        loadData();
    }

    @AfterEach
    public void tearDown() {
        cleanData();
    }
}
