package com.otus.spring.hw05jdbcdao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private List<Book> books;
    private Integer id;
    private String name;
}
