package com.otus.spring.hw11webflux.routing;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class ResponseUtils {
    public static <T> Mono<ServerResponse> okResponse(final Mono<T> body, final Class<T> bodyClass) {
        return ok().contentType(APPLICATION_JSON).body(body, bodyClass);
    }
}
