package com.otus.spring.hw13batch.repository;

import com.otus.spring.hw13batch.domain.Author;
import com.otus.spring.hw13batch.domain.Book;
import com.otus.spring.hw13batch.domain.Genre;
import com.otus.spring.hw13batch.entity.sql.BookSqlView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SqlDbBookRepository implements BookRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Book> findBooks() {
        final String sql = "SELECT b.id as book_id," +
                " b.name as book_name," +
                " b.year as book_year," +
                " g.id as genre_id," +
                " g.name as genre_name" +
                " a.id as author_id," +
                " a.name as author_name" +
                "FROM books b " +
                "INNER JOIN genres g ON b.genre_id = g.id " +
                "INNER JOIN authors a ON b.author_id = a.id";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BookSqlView.class)).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private Book map(BookSqlView view) {
        return Book.builder()
                .name(view.getName())
                .id(view.getId())
                .author(Author.builder()
                        .id(view.getAuthorId())
                        .name(view.getAuthorName())
                        .build())
                .genre(Genre.builder()
                        .id(view.getGenreId())
                        .name(view.getGenreName())
                        .build())
                .build();
    }
}
