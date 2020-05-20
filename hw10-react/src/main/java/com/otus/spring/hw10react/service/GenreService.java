package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.domain.Genre;
import com.otus.spring.hw10react.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw10react.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
import com.otus.spring.hw10react.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public FindGenresResponseDTO findAll() {
        return FindGenresResponseDTO.builder()
                .genres(mappingService.mapGenresToDtos(genreRepository.findAll()))
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<GenreDTO> findOne(final int id) {
        return genreRepository.findById(id)
                .map(mappingService::map);
    }
}
