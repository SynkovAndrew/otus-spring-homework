package com.otus.spring.hw11webflux.repository;

import com.otus.spring.hw11webflux.domain.Book;
import reactor.core.publisher.Flux;

import java.util.List;

public interface BookRepositoryCustom {
    Flux<Book> findByAuthor(String authorId);
}
