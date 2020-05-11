package com.otus.spring.hw08mongodb.repository;


import com.otus.spring.hw08mongodb.domain.Book;
import com.otus.spring.hw08mongodb.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> findByGenre(Genre genre);
}
