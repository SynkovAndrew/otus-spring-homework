package com.otus.spring.hw10react.service;

import com.otus.spring.hw10react.domain.Genre;
import com.otus.spring.hw10react.dto.genre.CreateOrUpdateGenreRequestDTO;
import com.otus.spring.hw10react.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw10react.dto.genre.GenreDTO;
import com.otus.spring.hw10react.exception.GenreNotFoundException;
import com.otus.spring.hw10react.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    @Transactional
    public GenreDTO createOrUpdate(final CreateOrUpdateGenreRequestDTO request) {
        return ofNullable(request.getId())
                .flatMap(id -> genreRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return genreRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> mappingService.map(genreRepository.save(
                        Genre.builder()
                                .name(request.getName())
                                .build()
                )));
    }

    @Transactional(readOnly = true)
    public GenreDTO find(final int genreId) throws GenreNotFoundException {
        return genreRepository.findById(genreId)
                .map(mappingService::map)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    @Transactional(readOnly = true)
    public FindGenresResponseDTO findAll() {
        return mappingService.mapGenresToResponse(genreRepository.findAll());
    }
}
