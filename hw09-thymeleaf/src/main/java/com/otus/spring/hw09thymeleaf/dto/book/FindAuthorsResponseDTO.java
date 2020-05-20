package com.otus.spring.hw09thymeleaf.dto.book;

import com.otus.spring.hw09thymeleaf.dto.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAuthorsResponseDTO {
    private List<AuthorDTO> books;
}
