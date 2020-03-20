package com.otus.spring.hw04.beans;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
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
