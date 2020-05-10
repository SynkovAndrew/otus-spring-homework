package com.otus.spring.hw08mongodb.service;

import com.otus.spring.hw08mongodb.domain.Book;
import com.otus.spring.hw08mongodb.dto.book.BookDTO;
import com.otus.spring.hw08mongodb.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw08mongodb.repository.AuthorRepository;
import com.otus.spring.hw08mongodb.repository.BookRepository;
import com.otus.spring.hw08mongodb.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    @Transactional
    public Optional<BookDTO> createOrUpdate(final CreateOrUpdateBookRequestDTO request) {
        return ofNullable(request.getId())
                .map(id -> bookRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            found.setYear(request.getYear());
                            ofNullable(request.getGenreId())
                                    .flatMap(genreRepository::findById)
                                    .ifPresent(found::setGenre);
                            ofNullable(request.getAuthorIds())
                                    .map(authorRepository::findAllByIdIn)
                                    .ifPresent(found::setAuthors);
                            return bookRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> {
                    final var book = bookRepository.save(Book.builder()
                            .name(request.getName())
                            .year(request.getYear())
                            .genre(ofNullable(request.getGenreId())
                                    .flatMap(genreRepository::findById)
                                    .orElse(null))
                            .authors(ofNullable(request.getAuthorIds())
                                    .map(authorRepository::findAllByIdIn)
                                    .orElse(null))
                            .build());
                    return Optional.ofNullable(mappingService.map(bookRepository.save(book)));
                });
    }

    @Transactional
    public Optional<BookDTO> deleteOne(final String id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return mappingService.map(book);
                });
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        return mappingService.mapBooksToDtos(bookRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findByAuthor(final String authorId) {
        return mappingService.mapBooksToDtos(bookRepository.findByAuthor(authorId));
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findByGenre(final String genreId) {
        return genreRepository.findById(genreId)
                .map(bookRepository::findByGenre)
                .map(mappingService::mapBooksToDtos)
                .orElse(emptyList());
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(final String id) {
        return bookRepository.findById(id)
                .map(mappingService::map);
    }
}
