package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw09thymeleaf.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/add")
    public ModelAndView addComment(final @ModelAttribute AddCommentToBookRequestDTO request) {
        commentService.add(request);
        final var bookId = request.getBookId();
        return new ModelAndView("redirect:/" + String.format("book/%d/comments", bookId));
    }

    @PostMapping("/comment/delete")
    public ModelAndView deleteComment(final @ModelAttribute RemoveCommentFromBookRequestDTO request) {
        commentService.remove(request);
        final var bookId = request.getBookId();
        return new ModelAndView("redirect:/" + String.format("book/%d/comments", bookId));
    }
}
