package com.otus.spring.hw09thymeleaf.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentToBookRequestDTO {
    private Integer bookId;
    private String comment;
}