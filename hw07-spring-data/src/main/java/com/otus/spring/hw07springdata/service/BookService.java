package com.otus.spring.hw07springdata.service;

import com.otus.spring.hw07springdata.dto.BookDTO;
import com.otus.spring.hw07springdata.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MappingService mappingService;

    public Optional<BookDTO> createOrUpdate(final BookDTO request) {
        return ofNullable(request.getId())
                .map(id -> bookRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            found.setGenre(mappingService.map(request.getGenre()));
                            found.setYear(request.getYear());
                            found.setAuthors(mappingService.mapDtosToAuthors(request.getAuthors()));
                            return bookRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> Optional.ofNullable(
                        mappingService.map(bookRepository.save(mappingService.map(request)))
                ));
    }

    public Optional<BookDTO> deleteOne(final int id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return mappingService.map(book);
                });
    }

    public List<BookDTO> findAll() {
        return mappingService.mapBooksToDtos(bookRepository.findAll());
    }

    public Optional<BookDTO> findOne(final int id) {
        return bookRepository.findById(id)
                .map(mappingService::map);
    }
}
