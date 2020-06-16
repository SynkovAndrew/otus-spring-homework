package com.otus.spring.hw11webflux.service;

import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.dto.book.BookDTO;
import com.otus.spring.hw11webflux.dto.book.CreateOrUpdateBookRequestDTO;
import com.otus.spring.hw11webflux.dto.book.FindBooksResponseDTO;
import com.otus.spring.hw11webflux.repository.AuthorRepository;
import com.otus.spring.hw11webflux.repository.BookRepository;
import com.otus.spring.hw11webflux.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    /*    @Transactional*/
    public Mono<BookDTO> create(final Mono<CreateOrUpdateBookRequestDTO> request) {
        return request.flatMap(req -> bookRepository.save(mappingService.map(req))
                .flatMap(this::enrich));
    }

    public Mono<Void> delete(final String bookId) {
        return bookRepository.deleteById(bookId);
    }

    private Mono<BookDTO> enrich(final Book book) {
        return Mono.zip(
                genreRepository.findById(book.getGenre()),
                authorRepository.findAllByIdIn(book.getAuthors()).collectList()
        )
                .map(joined -> mappingService.map(book, joined.getT1(), joined.getT2()));
    }

    /*    @Transactional(readOnly = true)*/
    public Mono<BookDTO> find(final String bookId) {
        return bookRepository.findById(bookId)
                .flatMap(this::enrich);
    }

    /*    @Transactional(readOnly = true)*/
    public Mono<FindBooksResponseDTO> findAll() {
        return bookRepository.findAll()
                .flatMap(this::enrich)
                .collectList()
                .map(mappingService::mapBookDtosToResponse);
    }

    /*    @Transactional*/
    public Mono<BookDTO> update(final String bookId, final Mono<CreateOrUpdateBookRequestDTO> request) {
        return request.flatMap(req -> bookRepository.findById(bookId)
                .flatMap(book -> {
                    book.setAuthors(req.getAuthorIds());
                    book.setGenre(req.getGenreId());
                    book.setName(req.getName());
                    book.setYear(req.getYear());
                    return bookRepository.save(book);
                })
                .flatMap(this::enrich));
    }


   /* private final AuthorRepository authorRepository;
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
    }*/

}
