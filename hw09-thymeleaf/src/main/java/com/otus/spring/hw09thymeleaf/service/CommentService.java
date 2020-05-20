package com.otus.spring.hw09thymeleaf.service;


import com.otus.spring.hw09thymeleaf.domain.Comment;
import com.otus.spring.hw09thymeleaf.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.CommentDTO;
import com.otus.spring.hw09thymeleaf.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw09thymeleaf.repository.BookRepository;
import com.otus.spring.hw09thymeleaf.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    @Transactional
    public Optional<CommentDTO> add(final AddCommentToBookRequestDTO request) {
        return bookRepository.findById(request.getBookId())
                .map(book -> {
                    final var comment = commentRepository.save(Comment.builder()
                            .book(book)
                            .value(request.getComment())
                            .build());
                    return of(mappingService.map(comment));
                })
                .orElse(empty());
    }

    @Transactional(readOnly = true)
    public Optional<CommentDTO> find(final int commentId) {
        return commentRepository.findById(commentId)
                .map(mappingService::map);

    }

    @Transactional(readOnly = true)
    public FindCommentsResponseDTO findAllByBookId(final int bookId) {
        final var comments = mappingService.mapCommentsToDtos(commentRepository.findAllByBookId(bookId));
        return FindCommentsResponseDTO.builder()
                .comments(comments)
                .build();
    }

    @Transactional
    public Optional<CommentDTO> remove(final RemoveCommentFromBookRequestDTO request) {
        return commentRepository.findById(request.getCommentId())
                .map(comment -> {
                    commentRepository.delete(comment);
                    return Optional.of(mappingService.map(comment));
                })
                .orElse(Optional.empty());
    }
}
