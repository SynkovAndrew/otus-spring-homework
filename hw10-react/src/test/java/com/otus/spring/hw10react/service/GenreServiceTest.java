package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
import com.otus.spring.hw10react.exception.GenreNotFoundException;
import com.otus.spring.hw10react.repository.GenreRepository;
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
public class GenreServiceTest {
    @Autowired
    private GenreRepository repository;
    private GenreService service;

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final var genre = CreateOrUpdateGenreRequestDTO.builder().name("Thriller").build();
        final var saved = service.createOrUpdate(genre);
        assertThat(saved).isNotNull();
        final List<GenreDTO> all = service.findAll().getGenres();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Find absent genre by id")
    public void findAbsentByIdTest() {
        assertThrows(GenreNotFoundException.class, () -> service.find(222));
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
    public void findByIdTest() throws GenreNotFoundException {
        final GenreDTO genre = service.find(4);
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(4);
        assertThat(genre).extracting("name").isEqualTo("Psychology");
    }

    @BeforeEach
    public void setUp() {
        service = new GenreService(repository, Mappers.getMapper(MappingService.class));
    }

    @Test
    @DisplayName("Update absent genre")
    public void updateAbsentTest() {
        assertThrows(GenreNotFoundException.class, () -> service.createOrUpdate(
                CreateOrUpdateGenreRequestDTO.builder().name("Non Fiction").id(222).build()));

        final List<GenreDTO> all = service.findAll().getGenres();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting(GenreDTO::getName).doesNotContain("Non Fiction");
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() throws GenreNotFoundException {
        final var result = service.createOrUpdate(CreateOrUpdateGenreRequestDTO.builder()
                .name("Non Fiction")
                .id(2)
                .build());
        assertThat(result).isNotNull();
        final GenreDTO genre = service.find(2);
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(2);
        assertThat(genre).extracting("name").isEqualTo("Non Fiction");
    }
}
