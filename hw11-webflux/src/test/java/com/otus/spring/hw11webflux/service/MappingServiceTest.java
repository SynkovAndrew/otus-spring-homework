package com.otus.spring.hw11webflux.service;

import com.otus.spring.hw11webflux.domain.Author;
import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.domain.Genre;
import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.BookDTO;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public class MappingServiceTest {
    private static MappingService mappingService;

    @BeforeAll
    public static void setUp() {
        mappingService = Mappers.getMapper(MappingService.class);
    }

    @Test
    public void mapAuthor() {
        final Author author = Author.builder().id("123").name("Vladimir").build();
        final AuthorDTO dto = mappingService.map(author);
        final Author initial = mappingService.map(dto);
        assertThat(author).isEqualToComparingFieldByField(initial);
    }

    @Test
    public void mapBook() {
        final Book book = Book.builder()
                .id("123")
                .name("The justice")
                .authors(newHashSet("1", "2"))
                .year(1251)
                .genre("1")
                .build();
        final BookDTO dto = mappingService.map(
                book,
                Genre.builder().id("1").name("Fantasy").build(),
                newArrayList(
                        Author.builder().id("1").name("Taras").build(),
                        Author.builder().id("2").name("Maxim").build()
                )
        );
        final Book initial = mappingService.map(dto);
        assertThat(book).isEqualToComparingFieldByField(initial);
    }

    @Test
    public void mapComments() {
        final List<Comment> comments = List.of(
                Comment.builder().id("1").value("Good").build(),
                Comment.builder().id("1").value("Excellent").build()
        );
        final List<CommentDTO> dtos = mappingService.mapCommentsToDtos(comments);
        final List<Comment> initial = mappingService.mapDtosToComments(dtos);
        assertThat(comments).containsExactlyElementsOf(initial);
    }
}
