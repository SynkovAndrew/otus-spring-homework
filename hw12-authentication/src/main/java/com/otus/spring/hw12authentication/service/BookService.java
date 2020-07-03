package com.otus.spring.hw12authentication.service;

import com.otus.spring.hw12authentication.domain.Book;
import com.otus.spring.hw12authentication.dto.book.BookDTO;
import com.otus.spring.hw12authentication.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw12authentication.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw12authentication.exception.BookNotFoundException;
import com.otus.spring.hw12authentication.repository.AuthorRepository;
import com.otus.spring.hw12authentication.repository.BookRepository;
import com.otus.spring.hw12authentication.repository.CommentRepository;
import com.otus.spring.hw12authentication.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BookDTO find(final int bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .map(mappingService::map)
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
