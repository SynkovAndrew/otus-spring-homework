package com.otus.spring.hw07springdata.dto.author;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAuthorRequestDTO {
    private Integer id;
    private String name;
}
