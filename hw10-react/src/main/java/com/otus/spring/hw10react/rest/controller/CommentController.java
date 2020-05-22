package com.otus.spring.hw10react.rest.controller;

import com.otus.spring.hw10react.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw10react.dto.comment.CommentDTO;
import com.otus.spring.hw10react.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.exception.CommentNotFoundException;
import com.otus.spring.hw10react.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public CommentDTO deleteAuthorFromBook(final @RequestBody RemoveCommentFromBookRequestDTO request)
            throws CommentNotFoundException {
        return commentService.remove(request);
    }
}
