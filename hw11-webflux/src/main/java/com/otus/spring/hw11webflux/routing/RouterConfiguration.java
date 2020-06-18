package com.otus.spring.hw11webflux.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> composedAuthorRoutes(AuthorHandler authorHandler) {
        return route()
                .GET("/api/v1/authors", accept(APPLICATION_JSON), authorHandler::findAll)
                .GET("/api/v1/book/{bookId}/authors", accept(APPLICATION_JSON), authorHandler::findBookAuthors)
                .GET("/api/v1/author/{authorId}", accept(APPLICATION_JSON), authorHandler::findOne)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> composedBookRoutes(BookHandler bookHandler) {
        return route()
                .GET("/api/v1/books", accept(APPLICATION_JSON), bookHandler::findAll)
                .GET("/api/v1/book/{bookId}", accept(APPLICATION_JSON), bookHandler::findOne)
                .POST("/api/v1/book", accept(APPLICATION_JSON), bookHandler::create)
                .PUT("/api/v1/book/{bookId}", accept(APPLICATION_JSON), bookHandler::update)
                .DELETE("/api/v1/book/{bookId}", accept(APPLICATION_JSON), bookHandler::delete)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> composedCommentRoutes(CommentHandler commentHandler) {
        return route()
                .GET("/api/v1/book/{bookId}/comments", accept(APPLICATION_JSON), commentHandler::findBookAuthors)
                .POST("/api/v1/comment", accept(APPLICATION_JSON), commentHandler::add)
                .DELETE("/api/v1/comment", accept(APPLICATION_JSON), commentHandler::remove)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> composedGenreRoutes(GenreHandler genreHandler) {
        return route()
                .GET("/api/v1/genres", accept(APPLICATION_JSON), genreHandler::findAll)
                .GET("/api/v1/genre/{genreId}", accept(APPLICATION_JSON), genreHandler::findOne)
                .build();
    }
}
