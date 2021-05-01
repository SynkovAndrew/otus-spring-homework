package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.SqlDbAuthor;
import com.otus.spring.hw13batch.entity.SqlDbBook;
import com.otus.spring.hw13batch.entity.SqlDbGenre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<SqlDbBook> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_YEAR = "year";

    @Override
    public SqlDbBook mapRow(ResultSet resultSet, int i) throws SQLException {
        return SqlDbBook.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .year(resultSet.getInt(COLUMN_YEAR))
                .build();
    }
}
