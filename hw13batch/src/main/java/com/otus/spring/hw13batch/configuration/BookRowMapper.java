package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.SqlDbBook;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<SqlDbBook> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_GENRE_ID = "genre_id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_YEAR = "year";

    @Override
    public SqlDbBook mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SqlDbBook(
                resultSet.getInt(COLUMN_ID),
                resultSet.getInt(COLUMN_GENRE_ID),
                resultSet.getString(COLUMN_NAME),
                resultSet.getInt(COLUMN_YEAR)
        );
    }
}
