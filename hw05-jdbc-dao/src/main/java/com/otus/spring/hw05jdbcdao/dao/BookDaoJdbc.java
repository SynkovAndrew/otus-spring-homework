package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import com.otus.spring.hw05jdbcdao.domain.Book;
import com.otus.spring.hw05jdbcdao.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void create(final Book book) {
        jdbcOperations.update(
                "insert into book (author_id, genre_id, name, year) " +
                        "values (:author_id, :genre_id, :name, :year)",
                of(
                        "author_id", ofNullable(book.getAuthor()).map(Author::getId).orElse(null),
                        "genre_id", ofNullable(book.getGenre()).map(Genre::getId).orElse(null),
                        "name", book.getName(),
                        "year", book.getYear()
                )
        );
    }

    @Override
    public void deleteById(final int id) {
        jdbcOperations.update("delete from book where id = :id", of("id", id));
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query("select * from book", of(),
                (resultSet, rowNumber) -> Book.builder()
                        .id(resultSet.getInt("id"))
                        .author(ofNullable(resultSet.getInt("author_id"))
                                .flatMap(authorDao::findById)
                                .orElse(null))
                        .genre(ofNullable(resultSet.getInt("genre_id"))
                                .flatMap(genreDao::findById)
                                .orElse(null))
                        .name(resultSet.getString("name"))
                        .year(resultSet.getInt("year"))
                        .build()
        );
    }

    @Override
    public Optional<Book> findById(final int id) {
        return jdbcOperations.queryForObject("select * from book where id = :id", of("id", id),
                (resultSet, rowNumber) -> Optional.of(
                        Book.builder()
                                .id(resultSet.getInt("id"))
                                .author(ofNullable(resultSet.getInt("author_id"))
                                        .flatMap(authorDao::findById)
                                        .orElse(null))
                                .genre(ofNullable(resultSet.getInt("genre_id"))
                                        .flatMap(genreDao::findById)
                                        .orElse(null))
                                .name(resultSet.getString("name"))
                                .year(resultSet.getInt("year"))
                                .build()
                ));
    }

    @Override
    public void update(final int id, final Book book) {
        jdbcOperations.update("update book set name = :name, year = :yeer, " +
                        "author_id = :author_id, genre_id = :genre_id where id = :id",
                of(
                        "author_id", ofNullable(book.getAuthor()).map(Author::getId).orElse(null),
                        "genre_id", ofNullable(book.getGenre()).map(Genre::getId).orElse(null),
                        "name", book.getName(),
                        "year", book.getYear()
                )
        );
    }
}
