package com.otus.spring.hw11webflux.routing;

import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw11webflux.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.otus.spring.hw11webflux.routing.ResponseUtils.okResponse;

@Service
@RequiredArgsConstructor
public class AuthorHandler {
    private final AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return okResponse(authorService.findAll(), FindAuthorsResponseDTO.class);
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return okResponse(authorService.find(request.pathVariable("authorId")), AuthorDTO.class);
    }
}
