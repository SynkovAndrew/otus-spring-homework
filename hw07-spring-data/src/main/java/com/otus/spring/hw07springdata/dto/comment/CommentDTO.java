package com.otus.spring.hw07springdata.dto.comment;

import com.otus.spring.hw07springdata.dto.book.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private BookDTO book;
    private Integer id;
    private String value;
}
