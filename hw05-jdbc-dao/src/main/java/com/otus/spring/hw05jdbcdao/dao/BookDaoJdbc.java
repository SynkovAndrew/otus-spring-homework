package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.domain.Genre;
import com.otus.spring.hw05jdbcdao.exception.ReferencedObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int create(final Book book) throws ReferencedObjectNotFoundException {
        try {
            return jdbcOperations.update(
                    "insert into book (author_id, genre_id, name, year) " +
                            "values (:author_id, :genre_id, :name, :year)",
                    of(
                            "author_id", ofNullable(book.getAuthor()).map(Author::getId).orElse(null),
                            "genre_id", ofNullable(book.getGenre()).map(Genre::getId).orElse(null),
                            "name", book.getName(),
                            "year", book.getYear()
                    )
            );
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedObjectNotFoundException();
        }
    }

    @Override
    public int deleteById(final int id) {
        return jdbcOperations.update("delete from book where id = :id", of("id", id));
    }

    @Override
    public List<Book> findAll(final Options options) {
        final Map<Integer, Author> authors = options.isSelectReferencedObjects() ?
                jdbcOperations.query("select id, name  from author", of(),
                        (resultSet, rowNumber) -> Author.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .build())
                        .stream()
                        .collect(toMap(Author::getId, Function.identity())) : null;

        final Map<Integer, Genre> genres = options.isSelectReferencedObjects() ?
                jdbcOperations.query("select id, name from genre", of(),
                        (resultSet, rowNumber) -> Genre.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .build())
                        .stream()
                        .collect(toMap(Genre::getId, Function.identity())) : null;

        return jdbcOperations.query("select id, author_id, genre_id, name, year  from book", of(),
                (resultSet, rowNumber) -> Book.builder()
                        .id(resultSet.getInt("id"))
                        .author(options.isSelectReferencedObjects() ?
                                authors.get(resultSet.getInt("author_id")) : null)
                        .genre(options.isSelectReferencedObjects() ?
                                genres.get(resultSet.getInt("genre_id")) : null)
                        .name(resultSet.getString("name"))
                        .year(resultSet.getInt("year"))
                        .build()
        );
    }

    @Override
    public Optional<Book> findById(final int id, final Options options) {
        try {
            return jdbcOperations.queryForObject(
                    "select id, author_id, genre_id, name, year from book where id = :id", of("id", id),
                    (resultSet, rowNumber) -> Optional.of(
                            Book.builder()
                                    .id(resultSet.getInt("id"))
                                    .author(options.isSelectReferencedObjects() ?
                                            Optional.of(resultSet.getInt("author_id"))
                                                    .map(authorId -> {
                                                        try {
                                                            return jdbcOperations.queryForObject(
                                                                    "select id, name from author where id = :author_id",
                                                                    of("author_id", authorId),
                                                                    (innerResultSet, innerRowNumber) ->
                                                                            Author.builder()
                                                                                    .id(innerResultSet
                                                                                            .getInt("id"))
                                                                                    .name(innerResultSet
                                                                                            .getString("name"))
                                                                                    .build()
                                                            );
                                                        } catch (EmptyResultDataAccessException e) {
                                                            return null;
                                                        }
                                                    })
                                                    .orElse(null) : null)
                                    .genre(options.isSelectReferencedObjects() ?
                                            Optional.of(resultSet.getInt("genre_id"))
                                                    .map(genreId -> {
                                                        try {
                                                            return jdbcOperations.queryForObject(
                                                                    "select id, name from genre where id = :genre_id",
                                                                    of("genre_id", genreId),
                                                                    (innerResultSet, innerRowNumber) ->
                                                                            Genre.builder()
                                                                                    .id(innerResultSet
                                                                                            .getInt("id"))
                                                                                    .name(innerResultSet
                                                                                            .getString("name"))
                                                                                    .build()
                                                            );
                                                        } catch (EmptyResultDataAccessException e) {
                                                            return null;
                                                        }
                                                    })
                                                    .orElse(null) : null)
                                    .name(resultSet.getString("name"))
                                    .year(resultSet.getInt("year"))
                                    .build()
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(final int id, final Book book) throws ReferencedObjectNotFoundException {
        try {
            return jdbcOperations.update("update book set name = :name, year = :year, " +
                            "author_id = :author_id, genre_id = :genre_id where id = :id",
                    of(
                            "id", id,
                            "author_id", ofNullable(book.getAuthor()).map(Author::getId).orElse(null),
                            "genre_id", ofNullable(book.getGenre()).map(Genre::getId).orElse(null),
                            "name", book.getName(),
                            "year", book.getYear()
                    )
            );
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedObjectNotFoundException();
        }
    }
}
