package com.otus.spring.hw12authentication.configuration;

import com.otus.spring.hw12authentication.service.MappingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mapstruct.factory.Mappers.getMapper;

@Configuration
public class ContextConfiguration {
    @Bean
    public MappingService mappingService() {
        return getMapper(MappingService.class);
    }
}
