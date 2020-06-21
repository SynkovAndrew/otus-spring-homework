package com.otus.spring.hw11webflux.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindBooksRequestDTO {
    private Integer authorId;
    private Integer genreId;
}
