package com.otus.spring.hw11webflux.routing;

import com.otus.spring.hw11webflux.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
import com.otus.spring.hw11webflux.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.otus.spring.hw11webflux.routing.ResponseUtils.okResponse;

@Service
@RequiredArgsConstructor
public class GenreHandler {
    private final GenreService genreService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return okResponse(genreService.findAll(), FindGenresResponseDTO.class);
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return okResponse(genreService.find(request.pathVariable("genreId")), GenreDTO.class);
    }
}
