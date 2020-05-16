package com.otus.spring.hw09thymeleaf.service;

import com.otus.spring.hw09thymeleaf.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.CommentDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw09thymeleaf.repository.BookRepository;
import com.otus.spring.hw09thymeleaf.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void addCommentTest() {
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
        assertThat(result1).isPresent();
        assertThat(result2).isPresent();
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
        assertThat(commentService.add(request)).isNotPresent();
    }

    @Test
    @DisplayName("Find comment of book")
    public void findCommentTest() {
        final var comment = commentService.find(3);
        assertThat(comment).isPresent();
        assertThat(comment).get().extracting(CommentDTO::getId).isEqualTo(3);
        assertThat(comment).get().extracting(CommentDTO::getValue).isEqualTo("Good book!");
    }

    @Test
    @DisplayName("Find all comments for certain book")
    public void findCommentsByBookId() {
        final var comments = commentService.findAllByBookId(3);
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(CommentDTO::getValue).containsOnly(
                "The good one. I have advised it to my father", "I dont really like it, coudnt even finish it...");
    }

    @Test
    @DisplayName("Remove comment")
    public void removeCommentTest() {
        final var response = commentService.remove(RemoveCommentFromBookRequestDTO.builder()
                .bookId(1)
                .commentId(1)
                .build());
        assertThat(response).isPresent();
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
