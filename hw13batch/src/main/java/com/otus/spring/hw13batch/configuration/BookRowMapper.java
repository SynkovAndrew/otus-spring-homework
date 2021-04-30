package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.Author;
import com.otus.spring.hw13batch.entity.Book;
import com.otus.spring.hw13batch.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    private final String COLUMN_BOOK_ID = "book_id";
    private final String COLUMN_BOOK_NAME = "book_name";
    private final String COLUMN_BOOK_YEAR = "book_year";
    private final String COLUMN_GENRE_ID = "genre_id";
    private final String COLUMN_GENRE_NAME = "genre_name";
    private final String COLUMN_AUTHOR_ID = "author_id";
    private final String COLUMN_AUTHOR_NAME = "author_name";

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        return Book.builder()
                .id(resultSet.getInt(COLUMN_BOOK_ID))
                .name(resultSet.getString(COLUMN_BOOK_NAME))
                .year(resultSet.getInt(COLUMN_BOOK_YEAR))
                .genre(Genre.builder()
                        .id(resultSet.getInt(COLUMN_GENRE_ID))
                        .name(resultSet.getString(COLUMN_GENRE_NAME))
                        .build())
                .author(Author.builder()
                        .id(resultSet.getInt(COLUMN_AUTHOR_ID))
                        .name(resultSet.getString(COLUMN_AUTHOR_NAME))
                        .build())
                .build();
    }
}
