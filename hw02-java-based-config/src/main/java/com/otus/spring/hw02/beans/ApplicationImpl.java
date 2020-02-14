package com.otus.spring.hw02.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApplicationImpl implements Application {
    private final MessageSource messageSource;
    private final QuestionService questionService;
    private final UI ui;
    private int score;

    @PostConstruct
    public void init() {
        run();
    }

    @Override
    public void run() {
        ui.print(messageSource.getMessage("hello.message", null, Locale.ENGLISH));

        final String name = String.join(" ", ui.read());
        final var questionAnswerMap = questionService.getQuestionAnswerMap();
        final int questionCount = questionAnswerMap.size();

        questionAnswerMap.forEach((question, answer) -> {
            ui.print(question);
            final List<String> read = ui.read();
            if (read.size() == 1 && Objects.equals(read.get(0).toLowerCase(), answer.toLowerCase())) {
                ui.print(messageSource.getMessage("right.answer.message", null, Locale.ENGLISH));
                score++;
            } else {
                ui.print(messageSource.getMessage("wrong.answer.message", null, Locale.ENGLISH));
            }
        });

        if (score > 3) {
            ui.print(messageSource.getMessage("congratulation.message",
                    new Object[]{name, score, questionCount}, Locale.ENGLISH));
        } else {
            ui.print(messageSource.getMessage("sorry.message",
                    new Object[]{name, score, questionCount}, Locale.ENGLISH));

        }
        ui.print("");
    }
}
