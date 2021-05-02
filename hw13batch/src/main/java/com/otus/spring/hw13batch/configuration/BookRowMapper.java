package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.BookSqlView;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<BookSqlView> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_YEAR = "year";

    @Override
    public BookSqlView mapRow(ResultSet resultSet, int i) throws SQLException {
        return BookSqlView.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .year(resultSet.getInt(COLUMN_YEAR))
                .build();
    }
}
