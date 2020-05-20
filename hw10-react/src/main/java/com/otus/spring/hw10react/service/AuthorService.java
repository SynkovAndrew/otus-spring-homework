package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.domain.Author;
import com.otus.spring.hw10react.domain.Book;
import com.otus.spring.hw10react.dto.author.AuthorDTO;
import com.otus.spring.hw10react.dto.author.CreateOrUpdateAuthorRequestDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw10react.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw10react.repository.AuthorRepository;
import com.otus.spring.hw10react.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final MappingService mappingService;

    @Transactional
    public Optional<AuthorDTO> createOrUpdate(final CreateOrUpdateAuthorRequestDTO request) {
        return ofNullable(request.getId())
                .map(id -> authorRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return authorRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> {
                    final var author = Author.builder()
                            .name(request.getName())
                            .build();
                    return of(mappingService.map(authorRepository.save(author)));
                });
    }

    @Transactional(readOnly = true)
    public FindAuthorsResponseDTO findAll(final FindAuthorsRequestDTO request) {
        return mappingService.mapGenresToResponse(authorRepository.findAll(request));
    }

    @Transactional(readOnly = true)
    public FindAuthorsResponseDTO findBookAuthors(final int bookId) {
        return bookRepository.findById(bookId)
                .map(Book::getAuthors)
                .map(mappingService::mapGenresToResponse)
                .orElse(FindAuthorsResponseDTO.builder()
                        .authors(emptyList())
                        .build());
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findOne(final int id) {
        return authorRepository.findById(id)
                .map(mappingService::map);
    }
}
