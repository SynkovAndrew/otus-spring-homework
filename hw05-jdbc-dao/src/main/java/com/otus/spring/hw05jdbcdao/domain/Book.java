package com.otus.spring.hw05jdbcdao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Author author;
    private Genre genre;
    private Integer id;
    private String name;
    private Integer year;
}
