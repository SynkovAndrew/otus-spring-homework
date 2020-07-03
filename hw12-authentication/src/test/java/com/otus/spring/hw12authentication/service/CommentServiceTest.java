package com.otus.spring.hw12authentication.service;

import com.otus.spring.hw12authentication.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw12authentication.dto.comment.CommentDTO;
import com.otus.spring.hw12authentication.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw12authentication.exception.BookNotFoundException;
import com.otus.spring.hw12authentication.exception.CommentNotFoundException;
import com.otus.spring.hw12authentication.repository.BookRepository;
import com.otus.spring.hw12authentication.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;

@DataJpaTest
public class CommentServiceTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    private CommentService commentService;

    @Test
    @DisplayName("Add comment to book")
    public void addCommentTest() throws BookNotFoundException {
        final var request1 = AddCommentToBookRequestDTO.builder()
                .bookId(4)
                .comment("What a beautiful thing!!!")
                .build();
        final var request2 = AddCommentToBookRequestDTO.builder()
                .bookId(4)
                .comment("The best!!!")
                .build();
        final var result1 = commentService.add(request1);
        final var result2 = commentService.add(request2);
        assertThat(result1).isNotNull();
        assertThat(result2).isNotNull();
        final var comments = commentRepository.findAllByBookId(4);
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting("id").doesNotContainNull();
        assertThat(comments).extracting("book.id").containsOnly(4);
        assertThat(comments).extracting("value")
                .containsOnly("What a beautiful thing!!!", "The best!!!");
    }

    @Test
    @DisplayName("Add comment to absent book")
    public void addCommentTest_bookNotFound() {
        final var request = AddCommentToBookRequestDTO.builder()
                .bookId(400)
                .comment("What a beautiful thing!!!")
                .build();
        assertThrows(BookNotFoundException.class, () -> commentService.add(request));
    }

    @Test
    @DisplayName("Find comment of book")
    public void findCommentTest() throws CommentNotFoundException {
        final var comment = commentService.find(3);
        assertThat(comment).isNotNull();
        assertThat(comment).extracting(CommentDTO::getId).isEqualTo(3);
        assertThat(comment).extracting(CommentDTO::getValue).isEqualTo("Good book!");
    }

    @Test
    @DisplayName("Find all comments for certain book")
    public void findCommentsByBookId() {
        final var comments = commentService.findAllByBookId(3).getComments();
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(CommentDTO::getValue).containsOnly(
                "The good one. I have advised it to my father", "I dont really like it, coudnt even finish it...");
    }

    @Test
    @DisplayName("Remove comment")
    public void removeCommentTest() throws CommentNotFoundException {
        final var response = commentService.remove(RemoveCommentFromBookRequestDTO.builder()
                .commentId(1)
                .build());
        assertThat(response).isNotNull();
        final var comments = commentRepository.findAllByBookId(1);
        assertThat(comments).hasSize(1);
        assertThat(comments).extracting("id").doesNotContainNull();
        assertThat(comments).extracting("book.id").containsOnly(1);
        assertThat(comments).extracting("value")
                .containsOnly("The greatest I ever read!");
    }

    @BeforeEach
    public void setUp() {
        commentService = new CommentService(bookRepository, commentRepository, getMapper(MappingService.class));
    }
}
