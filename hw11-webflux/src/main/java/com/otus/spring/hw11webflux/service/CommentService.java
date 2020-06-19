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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    public Mono<CommentDTO> add(final Mono<AddCommentToBookRequestDTO> request) {
        return request.flatMap(req -> bookRepository.findById(req.getBookId())
                .flatMap(book -> commentRepository.save(
                        Comment.builder()
                                .value(req.getComment())
                                .build())
                        .map(comment -> {
                            book.getComments().add(comment.getId());
                            bookRepository.save(book).subscribe();
                            return comment;
                        })))
                .map(mappingService::map);
    }

    public Mono<CommentDTO> find(final String commentId) {
        return commentRepository.findById(commentId)
                .map(mappingService::map);
    }

    public Mono<FindCommentsResponseDTO> findAllByBookId(final String bookId) {
        return bookRepository.findById(bookId)
                .map(book -> commentRepository.findByIdIn(book.getComments()))
                .flatMap(Flux::collectList)
                .map(mappingService::mapCommentsToResponse);
    }

    public Mono<Void> remove(final Mono<RemoveCommentFromBookRequestDTO> request) {
        return request.flatMap(req -> Mono.zip(
                bookRepository.findById(req.getBookId()),
                commentRepository.deleteById(req.getCommentId())
        ).map(tuple -> {
            tuple.getT1().getComments().remove(req.getCommentId());
            return tuple.getT2();
        }));
    }
}
