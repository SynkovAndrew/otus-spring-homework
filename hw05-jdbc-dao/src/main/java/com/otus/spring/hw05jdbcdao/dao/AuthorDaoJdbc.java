package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void create(final Author author) {
        jdbcOperations.update("insert into author (name) values (:name)", of("name", author.getName()));
    }

    @Override
    public void deleteById(final int id) {
        jdbcOperations.update("delete from author where id = :id", of("id", id));
    }

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query("select * from author", of(),
                (resultSet, rowNumber) -> Author.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build()
        );
    }

    @Override
    public Optional<Author> findById(final int id) {
        return jdbcOperations.queryForObject("select * from author where id = :id", of("id", id),
                (resultSet, rowNumber) -> Optional.of(
                        Author.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .build()
                ));
    }

    @Override
    public void update(final int id, final Author author) {
        jdbcOperations.update("update author set name = :name where id = :id",
                of("id", author.getId(), "name", author.getName()));
    }
}
