package com.otus.spring.hw09thymeleaf.service;

import com.otus.spring.hw09thymeleaf.domain.Author;
import com.otus.spring.hw09thymeleaf.dto.author.AuthorDTO;
import com.otus.spring.hw09thymeleaf.dto.author.CreateOrUpdateAuthorRequestDTO;
import com.otus.spring.hw09thymeleaf.dto.book.FindAuthorsRequestDTO;
import com.otus.spring.hw09thymeleaf.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
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
    public List<AuthorDTO> findAll(final FindAuthorsRequestDTO request) {
        return mappingService.mapAuthorsToDtos(authorRepository.findAll(request));
    }

    @Transactional(readOnly = true)
    public List<AuthorDTO> findAll() {
        return mappingService.mapAuthorsToDtos(authorRepository.findAll(
                FindAuthorsRequestDTO.builder()
                        .build()
        ));
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findOne(final int id) {
        return authorRepository.findById(id)
                .map(mappingService::map);
    }
}
