package com.otus.spring.hw07springdata.service;

import com.otus.spring.hw07springdata.domain.Author;
import com.otus.spring.hw07springdata.domain.Book;
import com.otus.spring.hw07springdata.domain.Comment;
import com.otus.spring.hw07springdata.domain.Genre;
import com.otus.spring.hw07springdata.dto.AuthorDTO;
import com.otus.spring.hw07springdata.dto.BookDTO;
import com.otus.spring.hw07springdata.dto.CommentDTO;
import com.otus.spring.hw07springdata.dto.GenreDTO;
import org.mapstruct.Mapper;

import java.util.List;

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

    List<Comment> mapDtosToComments(List<CommentDTO> comments);

    List<Author> mapDtosToAuthors(List<AuthorDTO> authors);

    List<Book> mapDtosToBooks(List<BookDTO> books);

    List<Genre> mapDtosToGenres(List<GenreDTO> genres);

    List<GenreDTO> mapGenresToDtos(List<Genre> genres);
}
