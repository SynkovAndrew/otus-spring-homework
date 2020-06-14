package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import com.otus.spring.hw11webflux.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw11webflux.repository.BookRepository;
import com.otus.spring.hw11webflux.repository.CommentRepository;
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
  /*  private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    @Transactional
    public Optional<CommentDTO> add(final AddCommentToBookRequestDTO request) {
        return bookRepository.findById(request.getBookId())
                .map(book -> {
                    final var comment = commentRepository.save(Comment.builder().value(request.getComment()).build());
                    book.getComments().add(comment);
                    bookRepository.save(book);
                    return of(mappingService.map(comment));
                })
                .orElse(empty());
    }

    @Transactional(readOnly = true)
    public Optional<CommentDTO> find(final String commentId) {
        return commentRepository.findById(commentId)
                .map(mappingService::map);

    }

    @Transactional(readOnly = true)
    public Set<CommentDTO> findAllByBookId(final String bookId) {
        return bookRepository.findById(bookId)
                .map(Book::getComments)
                .map(mappingService::mapCommentsToDtos)
                .orElse(Set.of());
    }

    @Transactional
    public Optional<CommentDTO> remove(final RemoveCommentFromBookRequestDTO request) {
        return bookRepository.findById(request.getBookId())
                .flatMap(book -> ofNullable(book.getComments()).orElse(emptySet()).stream()
                        .filter(c -> c.getId().equals(request.getCommentId()))
                        .findFirst()
                        .map(found -> {
                            book.getComments().remove(found);
                            bookRepository.save(book);
                            return mappingService.map(found);
                        }));
    }*/
}
