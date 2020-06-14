package com.otus.spring.hw11webflux.repository;


import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
    Flux<Book> findByGenre(Genre genre);
}
