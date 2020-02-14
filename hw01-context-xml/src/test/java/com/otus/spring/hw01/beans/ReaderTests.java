package com.otus.spring.hw01.beans;

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
        final List<String[]> lines = reader.readFile("questions.csv");

        assertThat(lines).isNotNull();
        assertThat(lines).hasSize(4);
        assertThat(lines.get(0)).contains("What is name of white ranger?", "Tommy");
        assertThat(lines.get(1)).contains("What is nickname of main hero in Matrix?", "Neo");
        assertThat(lines.get(2)).contains("What is name of Metallica drummer?", "Lars");
        assertThat(lines.get(3)).contains("What is your name of Godfather novel author?", "Mario");
    }
}
