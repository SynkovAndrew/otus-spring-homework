package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw11webflux.repository.AuthorRepository;
import com.otus.spring.hw11webflux.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final MappingService mappingService;

    public Mono<AuthorDTO> find(final String authorId) {
        return authorRepository.findById(authorId)
                .map(mappingService::map);
    }

    public Mono<FindAuthorsResponseDTO> findAll() {
        return authorRepository.findAll()
                .collectList()
                .map(mappingService::mapAuthorsToResponse);
    }

    public Mono<FindAuthorsResponseDTO> findBookAuthors(final String bookId) {
        return bookRepository.findById(bookId)
                .map(book -> authorRepository.findAllByIdIn(book.getAuthors()))
                .flatMap(Flux::collectList)
                .map(mappingService::mapAuthorsToResponse);
    }

    public Mono<FindAuthorsResponseDTO> findByIdNotIn(final Set<String> authorIds) {
        return authorRepository.findAllByIdNotIn(authorIds)
                .collectList()
                .map(mappingService::mapAuthorsToResponse);
    }
}
