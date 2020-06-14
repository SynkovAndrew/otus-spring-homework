package com.otus.spring.hw11webflux.routing;

import com.otus.spring.hw11webflux.dto.book.BookDTO;
import com.otus.spring.hw11webflux.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw11webflux.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.otus.spring.hw11webflux.routing.ResponseUtils.okResponse;

@Service
@RequiredArgsConstructor
public class BookHandler {
    private final BookService bookService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return okResponse(bookService.findAll(), FindBooksResponseDTO.class);
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return okResponse(bookService.find(request.pathVariable("bookId")), BookDTO.class);
    }
}
