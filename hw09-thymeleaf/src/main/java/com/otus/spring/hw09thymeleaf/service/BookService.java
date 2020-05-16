package com.otus.spring.hw09thymeleaf.service;

import com.otus.spring.hw09thymeleaf.domain.Book;
import com.otus.spring.hw09thymeleaf.dto.book.BookDTO;
import com.otus.spring.hw09thymeleaf.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw09thymeleaf.repository.AuthorRepository;
import com.otus.spring.hw09thymeleaf.repository.BookRepository;
import com.otus.spring.hw09thymeleaf.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
                                    .map(genreRepository::getOne)
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
                                    .map(genreRepository::getOne)
                                    .orElse(null))
                            .authors(ofNullable(request.getAuthorIds())
                                    .map(authorRepository::findAllByIdIn)
                                    .orElse(null))
                            .build());
                    return Optional.ofNullable(mappingService.map(bookRepository.save(book)));
                });
    }

    @Transactional
    public Optional<BookDTO> deleteOne(final int id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return mappingService.map(book);
                });
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findAll() {
        final var books = mappingService.mapBooksToDtos(bookRepository.findAll());
        return FindBooksResponseDTO.builder()
                .books(books)
                .build();
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findByAuthor(final int authorId) {
        final var books = mappingService.mapBooksToDtos(bookRepository.findByAuthorId(authorId));
        return FindBooksResponseDTO.builder()
                .books(books)
                .build();
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findByGenre(final int genreId) {
        final var books = mappingService.mapBooksToDtos(bookRepository.findByGenreId(genreId));
        return FindBooksResponseDTO.builder()
                .books(books)
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(final int id) {
        return bookRepository.findById(id)
                .map(mappingService::map);
    }
}
