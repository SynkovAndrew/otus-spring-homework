package com.otus.spring.hw12authentication.service;

import com.otus.spring.hw12authentication.domain.Author;
import com.otus.spring.hw12authentication.domain.Book;
import com.otus.spring.hw12authentication.dto.author.AuthorDTO;
import com.otus.spring.hw12authentication.dto.author.CreateOrUpdateAuthorRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw12authentication.exception.AuthorNotFoundException;
import com.otus.spring.hw12authentication.repository.AuthorRepository;
import com.otus.spring.hw12authentication.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final MappingService mappingService;

    @Transactional
    public AuthorDTO createOrUpdate(final CreateOrUpdateAuthorRequestDTO request) {
        return ofNullable(request.getId())
                .flatMap(id -> authorRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return authorRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> {
                    final var author = Author.builder()
                            .name(request.getName())
                            .build();
                    return mappingService.map(authorRepository.save(author));
                });
    }

    @Transactional(readOnly = true)
    public AuthorDTO find(final int authorId) throws AuthorNotFoundException {
        return authorRepository.findById(authorId)
                .map(mappingService::map)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
    }

    @Transactional(readOnly = true)
    public FindAuthorsResponseDTO findAll(final FindAuthorsRequestDTO request) {
        return mappingService.mapAuthorsToResponse(authorRepository.findAll(request));
    }

    @Transactional(readOnly = true)
    public FindAuthorsResponseDTO findBookAuthors(final int bookId) {
        return bookRepository.findById(bookId)
                .map(Book::getAuthors)
                .map(mappingService::mapAuthorsToResponse)
                .orElse(FindAuthorsResponseDTO.builder()
                        .authors(emptyList())
                        .build());
    }
}
