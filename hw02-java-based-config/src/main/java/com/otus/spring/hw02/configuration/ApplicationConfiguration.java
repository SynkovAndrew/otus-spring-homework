package com.otus.spring.hw02.configuration;

import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public static MessageSource messageSource() {
        final var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final var configurer = new PropertySourcesPlaceholderConfigurer();
        final var resources = new ClassPathResource[]{new ClassPathResource("application.properties")};
        configurer.setLocations(resources);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Bean
    public static Gson gson() {
        return new Gson();
    }
}
