package com.otus.spring.hw08mongodb.service;

import com.otus.spring.hw08mongodb.domain.Genre;
import com.otus.spring.hw08mongodb.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw08mongodb.dto.genre.GenreDTO;
import com.otus.spring.hw08mongodb.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    @Transactional
    public Optional<GenreDTO> createOrUpdate(final CreateOrUpdateGenreRequestDTO request) {
        return ofNullable(request.getId())
                .map(id -> genreRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return genreRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> {
                    final var genre = Genre.builder()
                            .name(request.getName())
                            .build();
                    return of(mappingService.map(genreRepository.save(genre)));
                });
    }

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        return mappingService.mapGenresToDtos(genreRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<GenreDTO> findOne(final String id) {
        return genreRepository.findById(id)
                .map(mappingService::map);
    }
}
