package com.otus.spring.hw09thymeleaf.controller;

import com.otus.spring.hw09thymeleaf.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw09thymeleaf.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/book/{bookId}/comment/add")
    public ModelAndView addComment(final @PathVariable("bookId") int bookId,
                                   final @ModelAttribute AddCommentToBookRequestDTO request) {
        request.setBookId(bookId);
        commentService.add(request);
        return new ModelAndView("redirect:/" + String.format("book/%d/comments", bookId));
    }

    @PostMapping("/book/{bookId}/comment/{commentId}/delete")
    public ModelAndView deleteComment(final @PathVariable("bookId") int bookId,
                                      final @PathVariable("commentId") int commentId) {
        commentService.remove(RemoveCommentFromBookRequestDTO.builder()
                .commentId(commentId)
                .build());
        return new ModelAndView("redirect:/" + String.format("book/%d/comments", bookId));
    }
}
