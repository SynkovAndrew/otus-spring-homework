package com.otus.spring.hw07springdata.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    private Integer id;
    private String name;
}
