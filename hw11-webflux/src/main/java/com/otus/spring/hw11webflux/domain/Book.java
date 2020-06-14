package com.otus.spring.hw11webflux.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
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
    @Builder.Default
    private Set<String> authors = new HashSet<>();

    @Builder.Default
    private Set<String> comments = new HashSet<>();

    @NotNull
    private String genre;

    @Id
    private String id;

    @NotEmpty
    private String name;

    private Integer year;
}
