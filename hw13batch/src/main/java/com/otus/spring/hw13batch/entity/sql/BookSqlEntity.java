package com.otus.spring.hw13batch.entity.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSqlEntity {
    private Integer id;
    private Integer genreId;
    private Integer authorId;
    private String name;
    private Integer year;
}
