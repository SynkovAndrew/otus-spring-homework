package com.otus.spring.hw07springdata.configuration;

import com.otus.spring.hw07springdata.service.MappingService;
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
