package com.otus.spring.hw12authentication.rest.controller;

import com.otus.spring.hw12authentication.dto.genre.FindGenresResponseDTO;
import com.otus.spring.hw12authentication.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/v1/genres")
    public FindGenresResponseDTO findAll() {
        return genreService.findAll();
    }
}
