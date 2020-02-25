package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.configuration.SettingsHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EasyMessageSource {
    private final MessageSource messageSource;
    private final SettingsHolder settingsHolder;

    public String get(final String propertyName) {
        return messageSource.getMessage(propertyName, null, settingsHolder.getLocale());
    }

    public String get(final String propertyName, final Object[] objects) {
        return messageSource.getMessage(propertyName, objects, settingsHolder.getLocale());
    }
}
