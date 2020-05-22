package com.otus.spring.hw10react.service;


import com.otus.spring.hw10react.domain.Comment;
import com.otus.spring.hw10react.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw10react.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw10react.dto.comment.CommentDTO;
import com.otus.spring.hw10react.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.exception.CommentNotFoundException;
import com.otus.spring.hw10react.repository.BookRepository;
import com.otus.spring.hw10react.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    @Transactional
    public CommentDTO add(final AddCommentToBookRequestDTO request) throws BookNotFoundException {
        return bookRepository.findById(request.getBookId())
                .map(book -> mappingService.map(commentRepository.save(
                        Comment.builder()
                                .book(book)
                                .value(request.getComment())
                                .build())
                ))
                .orElseThrow(() -> new BookNotFoundException(request.getBookId()));
    }

    @Transactional(readOnly = true)
    public CommentDTO find(final int commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId)
                .map(mappingService::map)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Transactional(readOnly = true)
    public FindCommentsResponseDTO findAllByBookId(final int bookId) {
        return mappingService.mapCommentsToResponse(commentRepository.findAllByBookId(bookId));
    }

    @Transactional
    public CommentDTO remove(final RemoveCommentFromBookRequestDTO request) throws CommentNotFoundException {
        final var commentId = request.getCommentId();
        return commentRepository.findById(commentId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return mappingService.map(comment);
                })
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
}
