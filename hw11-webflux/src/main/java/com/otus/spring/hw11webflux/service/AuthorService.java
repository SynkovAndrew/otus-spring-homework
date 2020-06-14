package com.otus.spring.hw11webflux.service;


import com.otus.spring.hw11webflux.dto.author.AuthorDTO;
import com.otus.spring.hw11webflux.dto.book.FindAuthorsResponseDTO;
import com.otus.spring.hw11webflux.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final MappingService mappingService;

    /*    @Transactional(readOnly = true)*/
    public Mono<AuthorDTO> find(final String authorId) {
        return authorRepository.findById(authorId)
                .map(mappingService::map);
    }

    /*    @Transactional(readOnly = true)*/
    public Mono<FindAuthorsResponseDTO> findAll() {
        return authorRepository.findAll()
                .collectList()
                .map(mappingService::mapAuthorsToResponse);
    }
}
