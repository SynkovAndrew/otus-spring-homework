package com.otus.spring.hw12authentication.service;


import com.otus.spring.hw12authentication.dto.author.AuthorDTO;
import com.otus.spring.hw12authentication.dto.author.CreateOrUpdateAuthorRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw12authentication.exception.AuthorNotFoundException;
import com.otus.spring.hw12authentication.repository.AuthorRepository;
import com.otus.spring.hw12authentication.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class AuthorServiceTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    private AuthorService service;

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        assertThrows(AuthorNotFoundException.class, () -> service.find(123));
    }

    @Test
    @DisplayName("Find all authors")
    public void findAllTest() {
        final List<AuthorDTO> all = service.findAll(FindAuthorsRequestDTO.builder().build()).getAuthors();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting(AuthorDTO::getId).containsOnly(1, 2, 3, 4);
        assertThat(all).extracting(AuthorDTO::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Find author by id")
    public void findByIdTest() throws AuthorNotFoundException {
        final AuthorDTO author = service.find(3);
        assertThat(author).isNotNull();
        assertThat(author).extracting(AuthorDTO::getId).isEqualTo(3);
        assertThat(author).extracting(AuthorDTO::getName).isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Save new author")
    public void saveTest() {
        final var author = CreateOrUpdateAuthorRequestDTO.builder().name("Lev Tolstoy").build();
        final var response = service.createOrUpdate(author);
        assertThat(response).isNotNull();
        final List<AuthorDTO> all = service.findAll(FindAuthorsRequestDTO.builder().build()).getAuthors();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting(AuthorDTO::getId).isNotNull();
        assertThat(all).extracting(AuthorDTO::getName).containsOnlyOnce("Lev Tolstoy");
    }

    @BeforeEach
    public void setUp() {
        service = new AuthorService(authorRepository, bookRepository, Mappers.getMapper(MappingService.class));
    }


    @Test
    @DisplayName("Update author")
    public void updateTest() throws AuthorNotFoundException {
        final var response = service.createOrUpdate(
                CreateOrUpdateAuthorRequestDTO.builder().name("Nikolai Michailowitsch Karamsin").id(1).build());
        assertThat(response).isNotNull();
        final AuthorDTO author = service.find(1);
        assertThat(author).isNotNull();
        assertThat(author).extracting(AuthorDTO::getId).isEqualTo(1);
        assertThat(author).extracting(AuthorDTO::getName).isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
