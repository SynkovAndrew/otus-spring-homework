package com.otus.spring.hw11webflux.dto.book;


import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
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
    private GenreDTO genre;
    private String id;
    private String name;
    private Integer year;
}
