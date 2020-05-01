package com.otus.spring.hw07springdata.dto;


import com.otus.spring.hw07springdata.domain.Author;
import com.otus.spring.hw07springdata.domain.Comment;
import com.otus.spring.hw07springdata.domain.Genre;
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
    private Set<Author> authors;
    private Set<Comment> comments;
    private Genre genre;
    private Integer id;
    private String name;
    private Integer year;
}
