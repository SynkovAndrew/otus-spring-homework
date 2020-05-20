package com.otus.spring.hw09thymeleaf.service;

import com.otus.spring.hw09thymeleaf.domain.Author;
import com.otus.spring.hw09thymeleaf.domain.Book;
import com.otus.spring.hw09thymeleaf.domain.Comment;
import com.otus.spring.hw09thymeleaf.domain.Genre;
import com.otus.spring.hw09thymeleaf.dto.author.AuthorDTO;
import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.CommentDTO;
import com.otus.spring.hw09thymeleaf.dto.genre.GenreDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Mapper
public interface MappingService {
    AuthorDTO map(Author author);

    Author map(AuthorDTO author);

    BookDTO map(Book book);

    Book map(BookDTO book);

    GenreDTO map(Genre genre);

    Genre map(GenreDTO genre);

    CommentDTO map(Comment comment);

    Comment map(CommentDTO comment);

    List<AuthorDTO> mapAuthorsToDtos(List<Author> authors);

    default List<BookDTO> mapBooksToDtos(List<Book> books) {
        return ofNullable(books).orElse(emptyList()).stream()
                .map(book -> BookDTO.builder()
                        .genre(map(book.getGenre()))
                        .id(book.getId())
                        .name(book.getName())
                        .year(book.getYear())
                        .build())
                .collect(Collectors.toList());
    }

    List<CommentDTO> mapCommentsToDtos(List<Comment> comments);

    Set<CommentDTO> mapCommentsToDtos(Set<Comment> comments);

    Set<Author> mapDtosToAuthors(Set<AuthorDTO> authors);

    List<Comment> mapDtosToComments(List<CommentDTO> comments);

    List<GenreDTO> mapGenresToDtos(List<Genre> genres);
}
