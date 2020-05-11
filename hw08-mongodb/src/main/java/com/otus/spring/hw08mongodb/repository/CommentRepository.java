package com.otus.spring.hw08mongodb.repository;


import com.otus.spring.hw08mongodb.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
