package com.otus.spring.hw11webflux.dto.book;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateBookRequestDTO {
    private Set<String> authorIds;
    private String genreId;
    private String name;
    private Integer year;
}
