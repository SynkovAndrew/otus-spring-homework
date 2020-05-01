package com.otus.spring.hw07springdata.service;

import com.otus.spring.hw07springdata.dto.AuthorDTO;
import com.otus.spring.hw07springdata.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final MappingService mappingService;

    public Optional<AuthorDTO> createOrUpdate(final AuthorDTO request) {
        return ofNullable(request.getId())
                .map(id -> authorRepository.findById(id)
                        .map(found -> {
                            found.setName(request.getName());
                            return authorRepository.save(found);
                        })
                        .map(mappingService::map))
                .orElseGet(() -> Optional.ofNullable(
                        mappingService.map(authorRepository.save(mappingService.map(request)))
                ));
    }

    public List<AuthorDTO> findAll() {
        return mappingService.mapAuthorsToDtos(authorRepository.findAll());
    }

    public Optional<AuthorDTO> findOne(final int id) {
        return authorRepository.findById(id)
                .map(mappingService::map);
    }
}
