package com.otus.spring.hw02.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ApplicationImpl implements Application {
    private final EasyMessageSource messageSource;
    private final QuestionService questionService;
    private final UI ui;
    private final int successScoreThreshold;
    private int score;

    public ApplicationImpl(final @Autowired EasyMessageSource messageSource,
                           final @Autowired QuestionService questionService,
                           final @Autowired UI ui,
                           final @Value("${success.score.threshold:3}") int successScoreThreshold) {
        this.messageSource = messageSource;
        this.questionService = questionService;
        this.ui = ui;
        this.successScoreThreshold = successScoreThreshold;
    }

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

            if (score > successScoreThreshold) {
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
