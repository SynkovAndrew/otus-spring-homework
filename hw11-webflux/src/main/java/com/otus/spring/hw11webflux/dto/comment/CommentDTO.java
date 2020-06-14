package com.otus.spring.hw11webflux.dto.comment;

import com.otus.spring.hw11webflux.dto.book.BookDTO;
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
    private String id;
    private String value;
}
