package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.SqlDbAuthor;
import com.otus.spring.hw13batch.entity.SqlDbGenre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<SqlDbAuthor> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";

    @Override
    public SqlDbAuthor mapRow(ResultSet resultSet, int i) throws SQLException {
        return SqlDbAuthor.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .build();
    }
}
