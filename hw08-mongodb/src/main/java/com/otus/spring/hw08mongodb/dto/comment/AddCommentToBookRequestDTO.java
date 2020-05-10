package com.otus.spring.hw08mongodb.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentToBookRequestDTO {
    private String bookId;
    private String comment;
}
