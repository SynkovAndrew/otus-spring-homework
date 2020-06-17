package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw11webflux.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import com.otus.spring.hw11webflux.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw11webflux.repository.BookRepository;
import com.otus.spring.hw11webflux.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    /*    @Transactional*/
    public Mono<CommentDTO> add(final Mono<AddCommentToBookRequestDTO> request) {
        return request.flatMap(req -> bookRepository.findById(req.getBookId())
                .flatMap(book -> commentRepository.save(
                        Comment.builder()
                                .value(req.getComment())
                                .build())
                        .map(comment -> {
                            book.getComments().add(comment.getId());
                            bookRepository.save(book);
                            return comment;
                        })))
                .map(mappingService::map);
    }

    /*    @Transactional(readOnly = true)*/
    public Mono<CommentDTO> find(final String commentId) {
        return commentRepository.findById(commentId)
                .map(mappingService::map);
    }

    /* @Transactional(readOnly = true)*/
    public Mono<FindCommentsResponseDTO> findAllByBookId(final String bookId) {
        return bookRepository.findById(bookId)
                .map(book -> commentRepository.findByIdIn(book.getComments()))
                .flatMap(Flux::collectList)
                .map(mappingService::mapCommentsToResponse);
    }

    /*@Transactional*/
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
    }
}
