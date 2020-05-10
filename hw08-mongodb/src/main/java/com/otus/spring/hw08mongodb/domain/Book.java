package com.otus.spring.hw08mongodb.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comments", "authors"})
public class Book {
    @DBRef(lazy = true)
    @Builder.Default
    private Set<Author> authors = new HashSet<>();

    @DBRef(lazy = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @NotNull
    @DBRef(lazy = true)
    private Genre genre;

    @Id
    private String id;

    @NotEmpty
    private String name;

    private Integer year;
}
