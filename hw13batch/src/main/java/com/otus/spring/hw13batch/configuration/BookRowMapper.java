package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.sql.BookSqlEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<BookSqlEntity> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_GENRE_ID = "genre_id";
    private final String COLUMN_AUTHOR_ID = "author_id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_YEAR = "year";

    @Override
    public BookSqlEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return BookSqlEntity.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .genreId(resultSet.getInt(COLUMN_GENRE_ID))
                .authorId(resultSet.getInt(COLUMN_AUTHOR_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .year(resultSet.getInt(COLUMN_YEAR))
                .build();
    }
}
