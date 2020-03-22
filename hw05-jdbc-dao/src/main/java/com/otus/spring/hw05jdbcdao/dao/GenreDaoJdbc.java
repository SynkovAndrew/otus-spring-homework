package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void create(final Genre genre) {
        jdbcOperations.update("insert into genre (name) values (:name)", of("name", genre.getName()));
    }

    @Override
    public void deleteById(final int id) {
        jdbcOperations.update("delete from genre where id = :id", of("id", id));
    }

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("select * from genre", of(),
                (resultSet, rowNumber) -> Genre.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build()
        );
    }

    @Override
    public Optional<Genre> findById(final int id) {
        try {
            return jdbcOperations.queryForObject("select * from genre where id = :id", of("id", id),
                    (resultSet, rowNumber) -> Optional.of(
                            Genre.builder()
                                    .id(resultSet.getInt("id"))
                                    .name(resultSet.getString("name"))
                                    .build()
                    ));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(final int id, final Genre genre) {
        jdbcOperations.update("update genre set name = :name where id = :id",
                of("id", id, "name", genre.getName()));
    }
}
