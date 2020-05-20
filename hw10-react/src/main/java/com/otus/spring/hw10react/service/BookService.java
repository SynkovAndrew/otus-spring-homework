package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.domain.Book;
import com.otus.spring.hw10react.dto.book.BookDTO;
import com.otus.spring.hw10react.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw10react.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw10react.exception.BookNotFoundException;
import com.otus.spring.hw10react.repository.AuthorRepository;
import com.otus.spring.hw10react.repository.BookRepository;
import com.otus.spring.hw10react.repository.CommentRepository;
import com.otus.spring.hw10react.repository.GenreRepository;
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
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    @Transactional
    public BookDTO addAuthor(final int bookId, final int authorId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .map(book -> {
                    authorRepository.findById(authorId)
                            .ifPresent(author -> book.getAuthors().add(author));
                    return mappingService.map(bookRepository.save(book));
                })
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Transactional
    public BookDTO create(final CreateOrUpdateBookRequestDTO request) {
        return mappingService.map(bookRepository.save(
                Book.builder()
                        .name(request.getName())
                        .year(request.getYear())
                        .genre(ofNullable(request.getGenreId())
                                .map(genreRepository::getOne)
                                .orElse(null))
                        .authors(ofNullable(request.getAuthorIds())
                                .map(authorRepository::findAllByIdIn)
                                .orElse(null))
                        .build()
        ));
    }

    @Transactional
    public BookDTO delete(final int bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .map(book -> {
                    commentRepository.deleteByBookId(bookId);
                    bookRepository.delete(book);
                    return mappingService.map(book);
                })
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Transactional
    public BookDTO deleteAuthor(final int bookId, final int authorId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .map(book -> {
                    final var authors = book.getAuthors();
                    authors.stream()
                            .filter(author -> author.getId() == authorId)
                            .findFirst()
                            .ifPresent(authors::remove);
                    return mappingService.map(bookRepository.save(book));
                })
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findAll() {
        return mappingService.mapBooksToResponse(bookRepository.findAll());
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findByAuthor(final int authorId) {
        return mappingService.mapBooksToResponse(bookRepository.findByAuthorId(authorId));
    }

    @Transactional(readOnly = true)
    public FindBooksResponseDTO findByGenre(final int genreId) {
        return mappingService.mapBooksToResponse(bookRepository.findByGenreId(genreId));
    }

    @Transactional(readOnly = true)
    public BookDTO findOne(final int bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .map(mappingService::map)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Transactional
    public BookDTO update(final int bookId, final CreateOrUpdateBookRequestDTO request) throws BookNotFoundException {
        return bookRepository.findById(bookId)
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
                .map(mappingService::map)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
