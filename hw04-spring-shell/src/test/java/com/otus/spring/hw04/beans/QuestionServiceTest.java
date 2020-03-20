package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.configuration.SettingsHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Execution(ExecutionMode.CONCURRENT)
public class QuestionServiceTest {
    private static Reader reader;
    private static SettingsHolder settingsHolder;
    private static QuestionService questionService;

    @BeforeAll
    public static void setUp() throws Exception {
        reader = mock(Reader.class);
        settingsHolder = mock(SettingsHolder.class);
        final var read = new ArrayList<String[]>();
        read.add(new String[]{"What is life?", "Game"});
        read.add(new String[]{"To be or not to be?", "Be"});
        read.add(new String[]{"Are you sure?", "Yes"});
        when(reader.readFile(anyString())).thenReturn(read);
        when(settingsHolder.getLanguage()).thenReturn("en");
        when(settingsHolder.getPathToQuestionFile()).thenReturn("en");
        when(settingsHolder.getLocale()).thenReturn(Locale.ENGLISH);
        when(settingsHolder.getSuccessScoreThreshold()).thenReturn(3);
        questionService = new QuestionServiceImpl(reader, settingsHolder);
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
