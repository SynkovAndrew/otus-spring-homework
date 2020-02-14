package com.otus.spring.hw02.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EasyMessageSource {
    private final MessageSource messageSource;
    private final Locale locale;

    public EasyMessageSource(final @Autowired MessageSource messageSource,
                             final @Value("${settings.language}") String language) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(language);
    }

    public String get(final String propertyName) {
        return messageSource.getMessage(propertyName, null, locale);
    }

    public String get(final String propertyName, final Object[] objects) {
        return messageSource.getMessage(propertyName, objects, locale);
    }
}
