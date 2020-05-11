package com.otus.spring.hw08mongodb.service;

import com.otus.spring.hw08mongodb.configuration.AbstractMongoDataLoader;
import com.otus.spring.hw08mongodb.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw08mongodb.dto.genre.GenreDTO;
import com.otus.spring.hw08mongodb.repository.GenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class GenreServiceDataLoader extends AbstractMongoDataLoader {
    @Autowired
    private GenreRepository repository;
    private GenreService service;

    @Autowired
    public GenreServiceDataLoader(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final var genre = CreateOrUpdateGenreRequestDTO.builder().name("Thriller").build();
        final var saved = service.createOrUpdate(genre);
        assertThat(saved).isPresent();
        final List<GenreDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Find absent genre by id")
    public void findAbsentByIdTest() {
        final Optional<GenreDTO> optional = service.findOne("222");
        assertThat(optional).isNotPresent();
    }

    @Test
    @DisplayName("Find all genres")
    public void findAllTest() {
        final List<GenreDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly("1", "2", "3", "4", "5", "6");
        assertThat(all).extracting("name")
                .containsOnly("Comedy", "History", "Detective", "Psychology", "Science Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find genre by id")
    public void findByIdTest() {
        final Optional<GenreDTO> genre = service.findOne("4");
        assertThat(genre).isPresent();
        assertThat(genre).get().extracting("id").isEqualTo("4");
        assertThat(genre).get().extracting("name").isEqualTo("Psychology");
    }

    @BeforeEach
    public void setUp() {
        service = new GenreService(repository, Mappers.getMapper(MappingService.class));
        loadData();
    }

    @AfterEach
    public void tearDown() {
        cleanData();
    }

    @Test
    @DisplayName("Update absent genre")
    public void updateAbsentTest() {
        final var response = service.createOrUpdate(
                CreateOrUpdateGenreRequestDTO.builder().name("Non Fiction").id("222").build());
        assertThat(response).isNotPresent();
        final List<GenreDTO> all = service.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting(GenreDTO::getName).doesNotContain("Non Fiction");
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() {
        final var result = service.createOrUpdate(CreateOrUpdateGenreRequestDTO.builder()
                .name("Non Fiction")
                .id("2")
                .build());
        assertThat(result).isPresent();
        final Optional<GenreDTO> genre = service.findOne("2");
        assertThat(genre).isPresent();
        assertThat(genre).get().extracting("id").isEqualTo("2");
        assertThat(genre).get().extracting("name").isEqualTo("Non Fiction");
    }
}
