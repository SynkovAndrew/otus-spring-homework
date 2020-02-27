package com.otus.spring.hw04.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static com.otus.spring.hw04.configuration.Constant.SPRING_PROFILE_TEST;

@ComponentScan("com.otus.spring.hw04.beans")
@ConfigurationPropertiesScan("com.otus.spring.hw04.configuration")
@SpringBootConfiguration
@ActiveProfiles(SPRING_PROFILE_TEST)
public class TestConfiguration {
}
