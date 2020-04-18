package com.otus.spring.hw06.repository;

import com.otus.spring.hw06.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryTest extends AbstractDataJpaTest<Genre> {
    private final GenreRepository repository;

    @Autowired
    protected GenreRepositoryTest(TestEntityManager entityManager,
                                  GenreRepository repository) {
        super(Genre.class, entityManager);
        this.repository = repository;
    }

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final Genre genre = Genre.builder().name("Thriller").build();

        repository.save(genre);

        final List<Genre> all = findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Delete absent genre")
    public void deleteAbsentTest() {
        repository.deleteById(156);

        final List<Genre> all = findAll();
        assertThat(all).size().isEqualTo(6);
    }

    @Test
    @DisplayName("Delete genre")
    public void deleteTest() {
        repository.deleteById(1);

        final List<Genre> all = findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting("id").containsOnly(2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("History", "Detective", "Psychology", "Science Fiction", "Fiction");
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
    @DisplayName("Update absent genre")
    public void updateAbsentTest() {
        final var optional = repository.update(11, Genre.builder().name("Non Fiction").build());
        assertThat(optional).isNotPresent();

        final var all = findAll();
        assertThat(all).extracting("name").doesNotContain("Non Fiction");
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() {
        final var optional = repository.update(2, Genre.builder().name("Non Fiction").build());
        assertThat(optional).isPresent();

        final Genre genre = findOne(2);
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(2);
        assertThat(genre).extracting("name").isEqualTo("Non Fiction");
    }
}
