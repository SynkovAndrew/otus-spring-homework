package com.otus.spring.hw08mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private String id;

    @NotEmpty
    private String name;
}
