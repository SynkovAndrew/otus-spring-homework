package com.otus.spring.hw06.repository;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
public class GenreRepositoryTest {
  /*  @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("Create new genre")
    public void createTest() {
        final Genre genre = Genre.builder().name("Thriller").build();

        final int result = genreDao.create(genre);
        assertThat(result).isEqualTo(1);

        final List<Genre> all = genreDao.findAll();
        assertThat(all).size().isEqualTo(7);
        assertThat(all).extracting("id").isNotNull();
        assertThat(all).extracting("name").containsOnlyOnce("Thriller");
    }

    @Test
    @DisplayName("Delete absent genre")
    public void deleteAbsentTest() {
        final int result = genreDao.deleteById(156);
        assertThat(result).isEqualTo(0);

        final List<Genre> all = genreDao.findAll();
        assertThat(all).size().isEqualTo(6);
    }

    @Test
    @DisplayName("Delete genre")
    public void deleteTest() {
        final int result = genreDao.deleteById(1);
        assertThat(result).isEqualTo(1);

        final List<Genre> all = genreDao.findAll();
        assertThat(all).size().isEqualTo(5);
        assertThat(all).extracting("id").containsOnly(2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("History", "Detective", "Psychology", "Science Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find absent genre by id")
    public void findAbsentByIdTest() {
        final Optional<Genre> optional = genreDao.findById(222);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find all genres")
    public void findAllTest() {
        final List<Genre> all = genreDao.findAll();
        assertThat(all).size().isEqualTo(6);
        assertThat(all).extracting("id").containsOnly(1, 2, 3, 4, 5, 6);
        assertThat(all).extracting("name")
                .containsOnly("Comedy", "History", "Detective", "Psychology", "Science Fiction", "Fiction");
    }

    @Test
    @DisplayName("Find genre by id")
    public void findByIdTest() {
        final Genre genre = genreDao.findById(4, Options.builder().selectReferencedObjects(true).build()).get();
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(4);
        assertThat(genre).extracting("name").isEqualTo("Psychology");
        assertThat(genre.getBooks()).extracting("name")
                .containsOnly("The Psychopathology of Everyday Life");
        assertThat(genre.getBooks()).extracting("year")
                .containsOnly(1904);
    }

    @Test
    @DisplayName("Update absent genre")
    public void updateAbsentTest() {
        final int result = genreDao.update(11, Genre.builder().name("Non Fiction").build());
        assertThat(result).isEqualTo(0);

        final Optional<Genre> optional = genreDao.findById(12);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Update genre")
    public void updateTest() {
        final int result = genreDao.update(2, Genre.builder().name("Non Fiction").build());
        assertThat(result).isEqualTo(1);

        final Genre genre = genreDao.findById(2).get();
        assertThat(genre).isNotNull();
        assertThat(genre).extracting("id").isEqualTo(2);
        assertThat(genre).extracting("name").isEqualTo("Non Fiction");
    }*/
}
