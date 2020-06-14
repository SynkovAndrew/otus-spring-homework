package com.otus.spring.hw11webflux.dto.book;

import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindCommentsResponseDTO {
    private Set<CommentDTO> comments;
}
