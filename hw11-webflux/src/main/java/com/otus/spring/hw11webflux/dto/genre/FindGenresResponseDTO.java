package com.otus.spring.hw11webflux.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindGenresResponseDTO {
    private List<GenreDTO> genres;
}
