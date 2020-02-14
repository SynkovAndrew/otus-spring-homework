package com.otus.spring.hw02.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EasyMessageSource {
    private final MessageSource messageSource;

    public String get(final String propertyName, final Locale locale) {
        return messageSource.getMessage(propertyName, null, locale);
    }

    public String get(final String propertyName, final Object[] objects, final Locale locale) {
        return messageSource.getMessage(propertyName, objects, locale);
    }
}
