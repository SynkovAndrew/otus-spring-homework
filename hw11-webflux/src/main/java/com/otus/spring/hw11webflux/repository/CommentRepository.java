package com.otus.spring.hw11webflux.repository;


import com.otus.spring.hw11webflux.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
