package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw10react.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw10react.dto.comment.CommentDTO;
import com.otus.spring.hw10react.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.exception.CommentNotFoundException;
import com.otus.spring.hw10react.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/v1/comment")
    public CommentDTO addCommentToBook(final @RequestBody AddCommentToBookRequestDTO request)
            throws BookNotFoundException {
        return commentService.add(request);
    }

    @DeleteMapping("/api/v1/comment")
    public CommentDTO deleteCommentFromBook(final @RequestBody RemoveCommentFromBookRequestDTO request)
            throws CommentNotFoundException {
        return commentService.remove(request);
    }

    @GetMapping("/api/v1/book/{bookId}/comments")
    public FindCommentsResponseDTO findAllByBookId(final @PathVariable("bookId") int bookId) {
        return commentService.findAllByBookId(bookId);
    }
}
