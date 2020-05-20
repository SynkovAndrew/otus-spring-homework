package com.otus.spring.hw10react.dto.book;

import com.otus.spring.hw10react.dto.author.AuthorDTO;
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
    private List<AuthorDTO> authors;
}
