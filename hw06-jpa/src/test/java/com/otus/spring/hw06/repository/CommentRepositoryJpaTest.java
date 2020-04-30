package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Book;
import com.otus.spring.hw06.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest extends AbstractDataJpaTest<Book> {
    private final CommentRepository commentRepository;

    @Autowired
    protected CommentRepositoryJpaTest(final TestEntityManager entityManager,
                                       final CommentRepository commentRepository) {
        super(Book.class, entityManager);
        this.commentRepository = commentRepository;
    }

    @Test
    @DisplayName("Find all comments for certain book")
    public void findCommentsByBookId() {
        final var comments = commentRepository.findAllByBookId(3);
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(Comment::getValue).containsOnly(
                "The good one. I have advised it to my father", "I dont really like it, coudnt even finish it...");
    }

    @Test
    @DisplayName("Add comment to book")
    public void addCommentTest() {
        final var comment1 = Comment.builder()
                .value("What a beautiful thing!!!")
                .build();
        final var comment2 = Comment.builder()
                .value("The best!!!")
                .build();
        commentRepository.add(comment1, 4);
        commentRepository.add(comment2, 4);

        final var book = findOne(4);
        assertThat(book).isNotNull();
        assertThat(book.getComments()).hasSize(2);
        assertThat(book.getComments()).extracting("id").doesNotContainNull();
        assertThat(book.getComments()).extracting("value")
                .containsOnly("What a beautiful thing!!!", "The best!!!");
    }

    @Test
    @DisplayName("Add comment to absent book")
    public void addCommentTest_bookNotFound() {
        final var comment = Comment.builder()
                .value("What a beautiful thing!!!")
                .build();
        assertThat(commentRepository.add(comment, 400)).isNotPresent();
    }

    @Test
    @DisplayName("Find comment of book")
    public void findCommentTest() {
        final var comment = commentRepository.find(3);
        assertThat(comment).isPresent();
        assertThat(comment).get().extracting(Comment::getId).isEqualTo(3);
        assertThat(comment).get().extracting(Comment::getValue).isEqualTo("Good book!");
    }

    @Test
    @DisplayName("Remove comment")
    public void removeCommentTest() {
        commentRepository.remove(1, 1);

        final var book = findOne(1);
        assertThat(book).isNotNull();
        assertThat(book.getComments()).hasSize(1);
        assertThat(book.getComments()).extracting("id").doesNotContainNull();
        assertThat(book.getComments()).extracting("value")
                .containsOnly("The greatest I ever read!");
    }
}
