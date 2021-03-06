package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.domain.Author;
import com.otus.spring.hw10react.domain.Book;
import com.otus.spring.hw10react.domain.Comment;
import com.otus.spring.hw10react.domain.Genre;
import com.otus.spring.hw10react.dto.author.AuthorDTO;
import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw10react.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw10react.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw10react.dto.comment.CommentDTO;
import com.otus.spring.hw10react.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
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

    List<AuthorDTO> mapAuthorsToDtos(Set<Author> authors);

    default FindAuthorsResponseDTO mapAuthorsToResponse(Set<Author> authors) {
        return FindAuthorsResponseDTO.builder()
                .authors(mapAuthorsToDtos(authors))
                .build();
    }

    default FindAuthorsResponseDTO mapAuthorsToResponse(List<Author> authors) {
        return FindAuthorsResponseDTO.builder()
                .authors(mapAuthorsToDtos(authors))
                .build();
    }

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

    default FindBooksResponseDTO mapBooksToResponse(List<Book> books) {
        return FindBooksResponseDTO.builder()
                .books(mapBooksToDtos(books))
                .build();
    }

    List<CommentDTO> mapCommentsToDtos(List<Comment> comments);

    Set<CommentDTO> mapCommentsToDtos(Set<Comment> comments);

    default FindCommentsResponseDTO mapCommentsToResponse(Set<Comment> comments) {
        return FindCommentsResponseDTO.builder()
                .comments(mapCommentsToDtos(comments))
                .build();
    }

    Set<Author> mapDtosToAuthors(Set<AuthorDTO> authors);

    List<Comment> mapDtosToComments(List<CommentDTO> comments);

    List<GenreDTO> mapGenresToDtos(List<Genre> genres);

    default FindGenresResponseDTO mapGenresToResponse(List<Genre> genres) {
        return FindGenresResponseDTO.builder()
                .genres(mapGenresToDtos(genres))
                .build();
    }
}
