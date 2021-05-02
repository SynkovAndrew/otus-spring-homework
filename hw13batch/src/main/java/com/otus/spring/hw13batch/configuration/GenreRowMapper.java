package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.GenreSqlEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreRowMapper implements RowMapper<GenreSqlEntity> {
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";

    @Override
    public GenreSqlEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return GenreSqlEntity.builder()
                .id(resultSet.getInt(COLUMN_ID))
                .name(resultSet.getString(COLUMN_NAME))
                .build();
    }
}
