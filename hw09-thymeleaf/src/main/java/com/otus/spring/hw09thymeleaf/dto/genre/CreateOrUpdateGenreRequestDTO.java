package com.otus.spring.hw09thymeleaf.dto.genre;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateGenreRequestDTO {
    private Integer id;
    private String name;
}
