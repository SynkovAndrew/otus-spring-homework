package com.otus.spring.hw11webflux.service;

import com.otus.spring.hw11webflux.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw11webflux.dto.genre.GenreDTO;
import com.otus.spring.hw11webflux.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    /*    @Transactional(readOnly = true)*/
    public Mono<GenreDTO> find(final String genreId) {
        return genreRepository.findById(genreId)
                .map(mappingService::map);
    }

    /*    @Transactional(readOnly = true)*/
    public Mono<FindGenresResponseDTO> findAll() {
        return genreRepository.findAll()
                .collectList()
                .map(mappingService::mapGenresToResponse);
    }
}
