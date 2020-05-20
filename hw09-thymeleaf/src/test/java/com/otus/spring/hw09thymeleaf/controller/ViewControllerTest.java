package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.author.AuthorDTO;
import com.otus.spring.hw09thymeleaf.dto.author.AuthorId;
import com.otus.spring.hw09thymeleaf.dto.book.*;
import com.otus.spring.hw09thymeleaf.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.CommentDTO;
import com.otus.spring.hw09thymeleaf.dto.genre.GenreDTO;
import com.otus.spring.hw09thymeleaf.service.AuthorService;
import com.otus.spring.hw09thymeleaf.service.BookService;
import com.otus.spring.hw09thymeleaf.service.CommentService;
import com.otus.spring.hw09thymeleaf.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ViewControllerTest {
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private GenreService genreService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void bookCommentsTest() throws Exception {
        setBookMock(5);
        when(genreService.findAll()).thenReturn(genres());
        when(commentService.findAllByBookId(5)).thenReturn(comments());
        mockMvc.perform(get("/book/5/comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-comments"))
                .andExpect(model().attribute("bookId", 5))
                .andExpect(model().attribute("bookName", "The War and Peace"))
                .andExpect(model().attribute("comments", comments().getComments()))
                .andExpect(model().attribute("addCommentRequest",
                        AddCommentToBookRequestDTO.builder()
                                .bookId(5)
                                .build()
                ));
    }

    private FindCommentsResponseDTO comments() {
        return FindCommentsResponseDTO.builder()
                .comments(
                        Set.of(
                                CommentDTO.builder()
                                        .id(1)
                                        .value("The greatest ever")
                                        .build(),
                                CommentDTO.builder()
                                        .id(2)
                                        .value("Nothing special")
                                        .build()
                        )
                )
                .build();
    }

    @Test
    public void editBookTest() throws Exception {
        setBookMock(33);
        when(authorService.findAll(
                FindAuthorsRequestDTO.builder()
                        .authorIdNotIn(Set.of(4))
                        .build()
        )).thenReturn(List.of(
                AuthorDTO.builder()
                        .id(5)
                        .name("Dostoevsky")
                        .build()
        ));
        when(genreService.findAll())
                .thenReturn(genres());
        mockMvc.perform(get("/book/33"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("genres", genres()))
                .andExpect(model().attribute("authorId", AuthorId.builder().build()))
                .andExpect(model().attribute("saveBookRequest",
                        CreateOrUpdateBookRequestDTO.builder()
                                .id(33)
                                .name("The War and Peace")
                                .year(1789)
                                .genreId(2)
                                .build())
                )
                .andExpect(model().attribute("book",
                        BookDTO.builder()
                                .id(33)
                                .name("The War and Peace")
                                .year(1789)
                                .authors(Set.of(
                                        AuthorDTO.builder()
                                                .id(4)
                                                .name("Tolstoy")
                                                .build()
                                ))
                                .genre(GenreDTO.builder()
                                        .id(2)
                                        .name("Novel")
                                        .build())
                                .build())
                )
                .andExpect(model().attribute("bookAuthors",
                        Set.of(
                                AuthorDTO.builder()
                                        .id(4)
                                        .name("Tolstoy")
                                        .build()
                        )
                ))
                .andExpect(model().attribute("authors",
                        List.of(
                                AuthorDTO.builder()
                                        .id(5)
                                        .name("Dostoevsky")
                                        .build()
                        )
                ));
    }

    private FindBooksResponseDTO findBooksResponse() {
        return FindBooksResponseDTO.builder()
                .books(List.of(
                        BookDTO.builder()
                                .id(1)
                                .name("Book 1")
                                .year(1789)
                                .authors(Set.of(
                                        AuthorDTO.builder()
                                                .id(4)
                                                .name("Tolstoy")
                                                .build()
                                ))
                                .genre(GenreDTO.builder()
                                        .id(2)
                                        .name("Comedy")
                                        .build())
                                .build(),
                        BookDTO.builder()
                                .id(2)
                                .name("Book 2")
                                .year(1712)
                                .authors(Set.of(
                                        AuthorDTO.builder()
                                                .id(2)
                                                .name("Vlasov")
                                                .build()
                                ))
                                .genre(GenreDTO.builder()
                                        .id(3)
                                        .name("Fiction")
                                        .build())
                                .build()
                ))
                .build();
    }

    private List<GenreDTO> genres() {
        return List.of(
                GenreDTO.builder()
                        .id(1)
                        .name("Tragedy")
                        .build(),
                GenreDTO.builder()
                        .id(2)
                        .name("Comedy")
                        .build()
        );
    }

    @Test
    public void index() throws Exception {
        when(bookService.findAll())
                .thenReturn(findBooksResponse());
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("books", findBooksResponse().getBooks()));

    }

    @Test
    public void newBookTest() throws Exception {
        when(genreService.findAll())
                .thenReturn(genres());
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("genres", genres()))
                .andExpect(model()
                        .attribute("saveBookRequest", CreateOrUpdateBookRequestDTO.builder().build()))
                .andExpect(model()
                        .attribute("book", BookDTO.builder().build()));
    }

    private void setBookMock(final int id) {
        when(bookService.findOne(id))
                .thenReturn(Optional.of(
                        BookDTO.builder()
                                .id(id)
                                .name("The War and Peace")
                                .year(1789)
                                .authors(Set.of(
                                        AuthorDTO.builder()
                                                .id(4)
                                                .name("Tolstoy")
                                                .build()
                                ))
                                .genre(GenreDTO.builder()
                                        .id(2)
                                        .name("Novel")
                                        .build())
                                .build()
                ));
    }
}
