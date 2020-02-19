package com.otus.spring.hw03.beans;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReaderTests {
    private static Reader reader;

    @BeforeAll
    public static void init() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFile() throws Exception {
        final List<String[]> lines = reader.readFile("questions_en.csv");
        assertThat(lines).isNotNull();
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).contains("What is life?", "Game");
        assertThat(lines.get(1)).contains("To be or not to be?", "Be");
        assertThat(lines.get(2)).contains("Are you sure?", "Yes");
    }
}
