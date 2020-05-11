package com.otus.spring.hw08mongodb.dto.genre;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateGenreRequestDTO {
    private String id;
    private String name;
}
