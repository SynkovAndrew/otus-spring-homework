package com.otus.spring.hw07springdata.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Set<AuthorDTO> authors;
    private Set<CommentDTO> comments;
    private GenreDTO genre;
    private Integer id;
    private String name;
    private Integer year;
}
