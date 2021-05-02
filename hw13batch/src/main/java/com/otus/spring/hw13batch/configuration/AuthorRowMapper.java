package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.AuthorSqlEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorRowMapper implements RowMapper<AuthorSqlEntity> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";

    @Override
    public AuthorSqlEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return AuthorSqlEntity.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .build();
    }
}
