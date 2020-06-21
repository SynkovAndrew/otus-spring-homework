package com.otus.spring.hw11webflux.repository;


import com.otus.spring.hw11webflux.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Set;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByIdIn(Set<String> ids);
}
