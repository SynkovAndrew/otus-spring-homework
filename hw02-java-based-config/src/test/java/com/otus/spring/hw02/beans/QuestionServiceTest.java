package com.otus.spring.hw02.beans;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceTest {
    private static Reader reader;
    private static QuestionService questionService;

    @BeforeAll
    public static void setUp() throws Exception {
        final var read = new ArrayList<String[]>();
        read.add(new String[]{"What is life?", "Game"});
        read.add(new String[]{"To be or not to be?", "Be"});
        read.add(new String[]{"Are you sure?", "Yes"});
        reader = mock(Reader.class);
        when(reader.readFile(anyString())).thenReturn(read);
        questionService = new QuestionServiceImpl(reader, "anyPath", "anyLanguage");
    }

    @Test
    public void getQuestionAnswerMapTest() throws Exception {
        questionService.loadQuestions();
        final Map<String, String> questionAnswerMap = questionService.getQuestionAnswerMap();

        assertThat(questionAnswerMap).isNotNull();
        assertThat(questionAnswerMap).hasSize(3);
        assertThat(questionAnswerMap.keySet()).containsExactlyInAnyOrder("What is life?", "To be or not to be?", "Are you sure?");
        assertThat(questionAnswerMap.values()).containsExactlyInAnyOrder("Game", "Be", "Yes");
    }
}
