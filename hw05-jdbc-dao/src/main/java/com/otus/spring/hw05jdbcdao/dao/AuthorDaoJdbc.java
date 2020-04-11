package com.otus.spring.hw05jdbcdao.dao;

import com.otus.spring.hw05jdbcdao.domain.Author;
import com.otus.spring.hw05jdbcdao.domain.Book;
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
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int create(final Author author) {
        return jdbcOperations.update("insert into author (name) values (:name)", of("name", author.getName()));
    }

    @Override
    public int deleteById(final int id) {
        return jdbcOperations.update("delete from author where id = :id", of("id", id));
    }

    @Override
    public List<Author> findAll(final Options options) {
        final Map<Integer, List<Book>> booksGroupedByAuthor = options.isSelectReferencedObjects() ?
                jdbcOperations.query("select id, name, author_id, year from book", of(),
                        (resultSet, rowNumber) -> Book.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .author(Author.builder().id(resultSet.getInt("author_id")).build())
                                .year(resultSet.getInt("year"))
                                .build())
                        .stream()
                        .collect(groupingBy(book -> book.getAuthor().getId())) : null;
        return jdbcOperations.query("select id, name from author", of(),
                (resultSet, rowNumber) -> Author.builder()
                        .id(resultSet.getInt("id"))
                        .books(options.isSelectReferencedObjects() ?
                                booksGroupedByAuthor.get(resultSet.getInt("id")) : null)
                        .name(resultSet.getString("name"))
                        .build()
        );
    }

    @Override
    public Optional<Author> findById(final int id, final Options options) {
        try {
            return jdbcOperations.queryForObject("select id, name from author where id = :id", of("id", id),
                    (resultSet, rowNumber) -> Optional.of(
                            Author.builder()
                                    .id(resultSet.getInt("id"))
                                    .books(options.isSelectReferencedObjects() ?
                                            jdbcOperations.query(
                                                    "select id, name, year from book where author_id = :author_id",
                                                    of("author_id", id),
                                                    (innerResultSet, innerRowNumber) -> Book.builder()
                                                            .id(innerResultSet.getInt("id"))
                                                            .name(innerResultSet.getString("name"))
                                                            .year(innerResultSet.getInt("year"))
                                                            .build()
                                            ) : null)
                                    .name(resultSet.getString("name"))
                                    .build()
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(final int id, final Author author) {
        return jdbcOperations.update("update author set name = :name where id = :id",
                of("id", id, "name", author.getName()));
    }
}
