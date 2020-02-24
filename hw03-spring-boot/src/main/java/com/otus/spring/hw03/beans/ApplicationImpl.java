package com.otus.spring.hw03.beans;

import com.otus.spring.hw03.configuration.SettingsHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

import static com.otus.spring.hw03.configuration.Constant.SPRING_PROFILE_TEST;

@Slf4j
@Service
@Profile("!" + SPRING_PROFILE_TEST)
@RequiredArgsConstructor
public class ApplicationImpl implements Application {
    private final EasyMessageSource messageSource;
    private final QuestionService questionService;
    private final UI ui;
    private final SettingsHolder settingsHolder;
    private int score;

    @PostConstruct
    public void init() {
        run();
    }

    @Override
    public void run() {
        try {
            questionService.loadQuestions();

            ui.print(messageSource.get("hello.message"));

            final String name = String.join(" ", ui.read());
            final var questionAnswerMap = questionService.getQuestionAnswerMap();
            final int questionCount = questionAnswerMap.size();

            questionAnswerMap.forEach((question, answer) -> {
                ui.print(question);
                final List<String> read = ui.read();
                if (read.size() == 1 && Objects.equals(read.get(0).toLowerCase(), answer.toLowerCase())) {
                    ui.print(messageSource.get("right.answer.message"));
                    score++;
                } else {
                    ui.print(messageSource.get("wrong.answer.message"));
                }
            });

            if (score > settingsHolder.getSuccessScoreThreshold()) {
                ui.print(messageSource.get("congratulation.message",
                        new Object[]{name, score, questionCount}));
            } else {
                ui.print(messageSource.get("sorry.message",
                        new Object[]{name, score, questionCount}));

            }
            ui.print("");
        } catch (Exception e) {
            ui.print(messageSource.get("cant.load.questions.error"));
            log.error("Failed to load questions:", e);
        }
    }
}
