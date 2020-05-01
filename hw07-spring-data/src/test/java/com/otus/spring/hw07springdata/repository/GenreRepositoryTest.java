package com.otus.spring.hw07springdata.repository;

import com.otus.spring.hw07springdata.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository repository;

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final Genre genre = Genre.builder().name("Thriller").build();

        repository.save(genre);

        final List<Genre> all = repository.findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Find absent genre by id")
    public void findAbsentByIdTest() {
        final Optional<Genre> optional = repository.findById(222);
        assertThat(optional).isNotPresent();
    }

    @Test
    @DisplayName("Find all genres")
    public void findAllTest() {
        final List<Genre> all = repository.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("Comedy", "History", "Detective", "Psychology", "Science Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find genre by id")
    public void findByIdTest() {
        final Optional<Genre> genre = repository.findById(4);
        assertThat(genre).isPresent();
        assertThat(genre).get().extracting("id").isEqualTo(4);
        assertThat(genre).get().extracting("name").isEqualTo("Psychology");
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() {
        repository.save(Genre.builder().name("Non Fiction").id(2).build());

        final Genre genre = repository.getOne(2);
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(2);
        assertThat(genre).extracting("name").isEqualTo("Non Fiction");
    }
}
