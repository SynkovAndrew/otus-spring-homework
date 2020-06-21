package com.otus.spring.hw11webflux.routing;

import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw11webflux.service.AuthorService;
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
public class AuthorHandler {
    private final AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return request.queryParam("authorIdNotIn")
                .map(param -> okResponse(authorService.findByIdNotIn(parse(param)), FindAuthorsResponseDTO.class))
                .orElseGet(() -> okResponse(authorService.findAll(), FindAuthorsResponseDTO.class));
    }

    public Mono<ServerResponse> findBookAuthors(ServerRequest request) {
        return okResponse(authorService.findBookAuthors(request.pathVariable("bookId")),
                FindAuthorsResponseDTO.class);
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return okResponse(authorService.find(request.pathVariable("authorId")), AuthorDTO.class);
    }

    private Set<String> parse(final String queryParam) {
        return Stream.of(queryParam.split(","))
                .map(StringUtils::trimToNull)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNumeric)
                .collect(toSet());
    }
}
