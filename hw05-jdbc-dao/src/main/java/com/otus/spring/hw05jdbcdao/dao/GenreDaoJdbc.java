package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.groupingBy;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int create(final Genre genre) {
        return jdbcOperations.update("insert into genre (name) values (:name)", of("name", genre.getName()));
    }

    @Override
    public int deleteById(final int id) {
        return jdbcOperations.update("delete from genre where id = :id", of("id", id));
    }

    @Override
    public List<Genre> findAll(final Options options) {
        final Map<Integer, List<Book>> booksGroupedByGenre = options.isSelectReferencedObjects() ? jdbcOperations.query(
                "select id, genre_id, name, year from book", of(),
                (resultSet, rowNumber) -> Book.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .genre(Genre.builder().id(resultSet.getInt("genre_id")).build())
                        .year(resultSet.getInt("year"))
                        .build())
                .stream()
                .collect(groupingBy(book -> book.getGenre().getId())) : null;

        return jdbcOperations.query("select id, name from genre", of(),
                (resultSet, rowNumber) -> Genre.builder()
                        .id(resultSet.getInt("id"))
                        .books(options.isSelectReferencedObjects() ?
                                booksGroupedByGenre.get(resultSet.getInt("id")) : null)
                        .name(resultSet.getString("name"))
                        .build()
        );
    }

    @Override
    public Optional<Genre> findById(final int id, final Options options) {
        try {
            return jdbcOperations.queryForObject("select id, name from genre where id = :id", of("id", id),
                    (resultSet, rowNumber) -> Optional.of(
                            Genre.builder()
                                    .id(resultSet.getInt("id"))
                                    .books(options.isSelectReferencedObjects() ?
                                            jdbcOperations.query(
                                                    "select id, name, year from book where genre_id = :genre_id",
                                                    of("genre_id", id),
                                                    (innerResultSet, innerRowNumber) -> Book.builder()
                                                            .id(innerResultSet.getInt("id"))
                                                            .name(innerResultSet.getString("name"))
                                                            .year(innerResultSet.getInt("year"))
                                                            .build()
                                            ) : null
                                    )
                                    .name(resultSet.getString("name"))
                                    .build()
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(final int id, final Genre genre) {
        return jdbcOperations.update("update genre set name = :name where id = :id",
                of("id", id, "name", genre.getName()));
    }
}
