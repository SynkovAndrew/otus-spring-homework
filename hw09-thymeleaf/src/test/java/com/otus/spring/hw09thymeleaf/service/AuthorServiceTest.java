package com.otus.spring.hw09thymeleaf.service;


import com.otus.spring.hw09thymeleaf.dto.author.AuthorDTO;
import com.otus.spring.hw09thymeleaf.dto.author.CreateOrUpdateAuthorRequestDTO;
import com.otus.spring.hw09thymeleaf.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorServiceTest {
    @Autowired
    private AuthorRepository authorRepository;
    private AuthorService service;

    @Test
    @DisplayName("Find absent author by id")
    public void findAbsentByIdTest() {
        final Optional<AuthorDTO> author = service.findOne(112);
        assertThat(author).isNotPresent();
    }

    @Test
    @DisplayName("Find all authors")
    public void findAllTest() {
        final List<AuthorDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting(AuthorDTO::getId).containsOnly(1, 2, 3, 4);
        assertThat(all).extracting(AuthorDTO::getName)
                .containsOnly("Erich Maria Remarque", "Ernest Hemingway", "George Orwell", "Sigmund Freud");
    }

    @Test
    @DisplayName("Find author by id")
    public void findByIdTest() {
        final Optional<AuthorDTO> author = service.findOne(3);
        assertThat(author).isPresent();
        assertThat(author).get().extracting(AuthorDTO::getId).isEqualTo(3);
        assertThat(author).get().extracting(AuthorDTO::getName).isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Save new author")
    public void saveTest() {
        final var author = CreateOrUpdateAuthorRequestDTO.builder().name("Lev Tolstoy").build();
        final var response = service.createOrUpdate(author);
        assertThat(response).isPresent();
        final List<AuthorDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting(AuthorDTO::getId).isNotNull();
        assertThat(all).extracting(AuthorDTO::getName).containsOnlyOnce("Lev Tolstoy");
    }

    @BeforeEach
    public void setUp() {
        service = new AuthorService(authorRepository, Mappers.getMapper(MappingService.class));
    }

    @Test
    @DisplayName("Update absent author")
    public void updateAbsentTest() {
        final var response = service.createOrUpdate(
                CreateOrUpdateAuthorRequestDTO.builder().name("Nikolai Michailowitsch Karamsin").id(111).build());
        assertThat(response).isNotPresent();
        final List<AuthorDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(4);
        assertThat(all).extracting(AuthorDTO::getName)
                .doesNotContain("Nikolai Michailowitsch Karamsin");
    }

    @Test
    @DisplayName("Update author")
    public void updateTest() {
        final var response = service.createOrUpdate(
                CreateOrUpdateAuthorRequestDTO.builder().name("Nikolai Michailowitsch Karamsin").id(1).build());
        assertThat(response).isPresent();
        final Optional<AuthorDTO> author = service.findOne(1);
        assertThat(author).isPresent();
        assertThat(author).get().extracting(AuthorDTO::getId).isEqualTo(1);
        assertThat(author).get().extracting(AuthorDTO::getName).isEqualTo("Nikolai Michailowitsch Karamsin");
    }
}
