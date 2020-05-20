package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
import com.otus.spring.hw10react.repository.GenreRepository;
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
public class GenreServiceTest {
    @Autowired
    private GenreRepository repository;
    private GenreService service;

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final var genre = CreateOrUpdateGenreRequestDTO.builder().name("Thriller").build();
        final var saved = service.createOrUpdate(genre);
        assertThat(saved).isPresent();
        final List<GenreDTO> all = service.findAll().getGenres();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Find absent genre by id")
    public void findAbsentByIdTest() {
        final Optional<GenreDTO> optional = service.findOne(222);
        assertThat(optional).isNotPresent();
    }

    @Test
    @DisplayName("Find all genres")
    public void findAllTest() {
        final List<GenreDTO> all = service.findAll().getGenres();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("Comedy", "History", "Detective", "Psychology", "Science Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find genre by id")
    public void findByIdTest() {
        final Optional<GenreDTO> genre = service.findOne(4);
        assertThat(genre).isPresent();
        assertThat(genre).get().extracting("id").isEqualTo(4);
        assertThat(genre).get().extracting("name").isEqualTo("Psychology");
    }

    @BeforeEach
    public void setUp() {
        service = new GenreService(repository, Mappers.getMapper(MappingService.class));
    }

    @Test
    @DisplayName("Update absent genre")
    public void updateAbsentTest() {
        final var response = service.createOrUpdate(
                CreateOrUpdateGenreRequestDTO.builder().name("Non Fiction").id(222).build());
        assertThat(response).isNotPresent();
        final List<GenreDTO> all = service.findAll().getGenres();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting(GenreDTO::getName).doesNotContain("Non Fiction");
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() {
        final var result = service.createOrUpdate(CreateOrUpdateGenreRequestDTO.builder()
                .name("Non Fiction")
                .id(2)
                .build());
        assertThat(result).isPresent();
        final Optional<GenreDTO> genre = service.findOne(2);
        assertThat(genre).isPresent();
        assertThat(genre).get().extracting("id").isEqualTo(2);
        assertThat(genre).get().extracting("name").isEqualTo("Non Fiction");
    }
}
