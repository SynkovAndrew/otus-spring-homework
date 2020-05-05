package com.otus.spring.hw07springdata.dto.book;


import com.otus.spring.hw07springdata.dto.author.AuthorDTO;
import com.otus.spring.hw07springdata.dto.comment.CommentDTO;
import com.otus.spring.hw07springdata.dto.genre.GenreDTO;
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
