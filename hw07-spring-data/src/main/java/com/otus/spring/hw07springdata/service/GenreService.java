package com.otus.spring.hw07springdata.service;

import com.otus.spring.hw07springdata.dto.GenreDTO;
import com.otus.spring.hw07springdata.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MappingService mappingService;

    public Optional<GenreDTO> createOrUpdate(final GenreDTO request) {
        return ofNullable(request.getId())
                .map(id -> genreRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return genreRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> Optional.ofNullable(
                        mappingService.map(genreRepository.save(mappingService.map(request)))
                ));
    }

    public List<GenreDTO> findAll() {
        return mappingService.mapGenresToDtos(genreRepository.findAll());
    }

    public Optional<GenreDTO> findOne(final int id) {
        return genreRepository.findById(id)
                .map(mappingService::map);
    }
}
