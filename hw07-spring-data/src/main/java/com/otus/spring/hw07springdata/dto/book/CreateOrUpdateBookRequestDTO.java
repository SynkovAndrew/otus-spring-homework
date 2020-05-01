package com.otus.spring.hw07springdata.dto.book;


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
    private Integer id;
    private String name;
    private Integer year;
}
