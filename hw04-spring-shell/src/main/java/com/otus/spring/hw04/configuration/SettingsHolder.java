package com.otus.spring.hw04.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;
import java.util.Locale;

import static java.util.Locale.forLanguageTag;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "settings")
public class SettingsHolder {
    @NotBlank
    private final String language;
    @NotBlank
    private final String pathToQuestionFile;
    private final int successScoreThreshold;

    public Locale getLocale() {
        return forLanguageTag(language);
    }
}
