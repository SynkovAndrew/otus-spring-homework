package com.otus.spring.hw13batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSqlView {
    private Integer id;
    private String name;
    private Integer year;
    private Integer authorId;
    private String authorName;
    private Integer genreId;
    private String genreName;
}
