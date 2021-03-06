package com.otus.spring.hw07springdata.service;


import com.otus.spring.hw07springdata.domain.Book;
import com.otus.spring.hw07springdata.domain.Comment;
import com.otus.spring.hw07springdata.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw07springdata.dto.comment.CommentDTO;
import com.otus.spring.hw07springdata.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw07springdata.repository.BookRepository;
import com.otus.spring.hw07springdata.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Optional.*;

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
    public Set<CommentDTO> findAllByBookId(final int bookId) {
        return mappingService.mapCommentsToDtos(commentRepository.findAllByBookId(bookId));
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
