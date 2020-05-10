package com.otus.spring.hw08mongodb.service;

import com.otus.spring.hw08mongodb.domain.Author;
import com.otus.spring.hw08mongodb.domain.Book;
import com.otus.spring.hw08mongodb.domain.Comment;
import com.otus.spring.hw08mongodb.domain.Genre;
import com.otus.spring.hw08mongodb.dto.author.AuthorDTO;
import com.otus.spring.hw08mongodb.dto.book.BookDTO;
import com.otus.spring.hw08mongodb.dto.comment.CommentDTO;
import com.otus.spring.hw08mongodb.dto.genre.GenreDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

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

    List<BookDTO> mapBooksToDtos(List<Book> books);

    List<CommentDTO> mapCommentsToDtos(List<Comment> comments);

    Set<CommentDTO> mapCommentsToDtos(Set<Comment> comments);

    List<Author> mapDtosToAuthors(List<AuthorDTO> authors);

    Set<Author> mapDtosToAuthors(Set<AuthorDTO> authors);

    List<Book> mapDtosToBooks(List<BookDTO> books);

    List<Comment> mapDtosToComments(List<CommentDTO> comments);

    Set<Comment> mapDtosToComments(Set<CommentDTO> comments);

    List<Genre> mapDtosToGenres(List<GenreDTO> genres);

    List<GenreDTO> mapGenresToDtos(List<Genre> genres);
}
