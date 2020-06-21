package com.otus.spring.hw11webflux.routing;

import com.otus.spring.hw11webflux.dto.book.FindCommentsResponseDTO;
import com.otus.spring.hw11webflux.dto.comment.AddCommentToBookRequestDTO;
import com.otus.spring.hw11webflux.dto.comment.CommentDTO;
import com.otus.spring.hw11webflux.dto.comment.RemoveCommentFromBookRequestDTO;
import com.otus.spring.hw11webflux.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static com.otus.spring.hw11webflux.routing.ResponseUtils.okResponse;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class CommentHandler {
    private final CommentService commentService;

    public Mono<ServerResponse> add(ServerRequest request) {
        return okResponse(commentService.add(request.bodyToMono(AddCommentToBookRequestDTO.class)), CommentDTO.class);
    }

    public Mono<ServerResponse> findBookAuthors(ServerRequest request) {
        return okResponse(commentService.findAllByBookId(request.pathVariable("bookId")),
                FindCommentsResponseDTO.class);
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return okResponse(commentService.find(request.pathVariable("commentId")), CommentDTO.class);
    }

    private Set<String> parse(final String queryParam) {
        return Stream.of(queryParam.split(","))
                .map(StringUtils::trimToNull)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNumeric)
                .collect(toSet());
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        return okResponse(commentService.remove(request.bodyToMono(RemoveCommentFromBookRequestDTO.class)), Void.class);
    }
}
