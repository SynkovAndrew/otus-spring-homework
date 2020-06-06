package com.otus.spring.hw10react.dto.book;


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
    private Set<Integer> authorIds;
    private Integer genreId;
    private String name;
    private Integer year;
}
