package com.otus.spring.hw11webflux.configuration;

import com.otus.spring.hw11webflux.service.MappingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mapstruct.factory.Mappers.getMapper;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class ContextConfiguration {
    @Bean
    public MappingService mappingService() {
        return getMapper(MappingService.class);
    }
}
