package com.otus.spring.hw12authentication.rest.controller;

import com.otus.spring.hw12authentication.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw12authentication.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw12authentication.dto.comment.CommentDTO;
import com.otus.spring.hw12authentication.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw12authentication.exception.BookNotFoundException;
import com.otus.spring.hw12authentication.exception.CommentNotFoundException;
import com.otus.spring.hw12authentication.service.CommentService;
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
