package com.otus.spring.hw11webflux.dto.book;

import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAuthorsResponseDTO {
    private Set<AuthorDTO> authors;
}
