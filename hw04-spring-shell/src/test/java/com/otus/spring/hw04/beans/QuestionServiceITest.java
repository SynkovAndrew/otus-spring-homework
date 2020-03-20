package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.configuration.AuthHolder;
import com.otus.spring.hw04.configuration.SettingsHolder;
import com.otus.spring.hw04.configuration.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static com.otus.spring.hw04.configuration.Constant.SPRING_PROFILE_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfiguration.class})
@ActiveProfiles(SPRING_PROFILE_TEST)
public class QuestionServiceITest {
    @Autowired
    private QuestionService questionService;

    @Test
    public void getQuestionAnswerMapTest() throws Exception {
        questionService.loadQuestions();
        final Map<String, String> questionAnswerMap = questionService.getQuestionAnswerMap();

        assertThat(questionAnswerMap).isNotNull();
        assertThat(questionAnswerMap).hasSize(3);
        assertThat(questionAnswerMap.keySet()).containsExactlyInAnyOrder("What is life?", "To be or not to be?", "Are you sure?");
        assertThat(questionAnswerMap.values()).containsExactlyInAnyOrder("Game", "Be", "Yes");
    }

    public static class TestConfiguration {
        // nothing
    }
}
