package com.otus.spring.hw11webflux.service;

import com.otus.spring.hw11webflux.domain.Author;
import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.domain.Genre;
import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.*;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import com.otus.spring.hw11webflux.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

@Mapper
public interface MappingService {
    AuthorDTO map(Author author);

    Author map(AuthorDTO author);

    default Book map(CreateOrUpdateBookRequestDTO request) {
        return Book.builder()
                .authors(request.getAuthorIds())
                .genre(request.getGenreId())
                .name(request.getName())
                .year(request.getYear())
                .build();
    }

    default BookDTO map(Book book, final Genre genre, final List<Author> authors) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .year(book.getYear())
                .genre(map(genre))
                .authors(mapAuthorsToDtos(authors))
                .build();
    }

    default Book map(BookDTO book) {
        return Book.builder()
                .id(book.getId())
                .name(book.getName())
                .year(book.getYear())
                .genre(ofNullable(book.getGenre())
                        .map(GenreDTO::getId)
                        .orElse(null))
                .authors(ofNullable(book.getAuthors()).orElse(emptySet())
                        .stream()
                        .map(AuthorDTO::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    GenreDTO map(Genre genre);

    Genre map(GenreDTO genre);

    CommentDTO map(Comment comment);

    Comment map(CommentDTO comment);

    Set<AuthorDTO> mapAuthorsToDtos(List<Author> authors);

    Set<AuthorDTO> mapAuthorsToDtos(Set<Author> authors);

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

    default FindBooksResponseDTO mapBookDtosToResponse(List<BookDTO> books) {
        return FindBooksResponseDTO.builder()
                .books(books)
                .build();
    }

    List<CommentDTO> mapCommentsToDtos(List<Comment> comments);

    Set<CommentDTO> mapCommentsToDtos(Set<Comment> comments);

    default FindCommentsResponseDTO mapCommentsToResponse(List<Comment> comments) {
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
