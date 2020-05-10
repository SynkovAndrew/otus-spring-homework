package com.otus.spring.hw08mongodb.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveCommentFromBookRequestDTO {
    private String bookId;
    private String commentId;
}
