package com.otus.spring.hw01.beans;

import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class ApplicationImpl implements Application {
    private final QuestionService questionService;
    private final UI ui;
    @Setter
    private String congratulationMessage;
    @Setter
    private String helloMessage;
    @Setter
    private String rightAnswerMessage;
    private int score;
    @Setter
    private String sorryMessage;
    @Setter
    private String wrongAnswerMessage;

    public ApplicationImpl(final QuestionService questionService, final UI ui) {
        this.questionService = questionService;
        this.ui = ui;
    }

    @Override
    public void run() {
        ui.print(helloMessage);
        final String name = String.join(" ", ui.read());

        final var questionAnswerMap = questionService.getQuestionAnswerMap();
        final int questionCount = questionAnswerMap.size();
        questionAnswerMap.forEach((question, answer) -> {
            ui.print(question);
            final List<String> read = ui.read();
            if (read.size() == 1 && Objects.equals(read.get(0).toLowerCase(), answer.toLowerCase())) {
                ui.print(rightAnswerMessage);
                score++;
            } else {
                ui.print(wrongAnswerMessage);
            }
        });

        if (score > 3) {
            ui.print(String.format(congratulationMessage, name, score, questionCount));
        } else {
            ui.print(String.format(sorryMessage, name, score, questionCount));

        }
        ui.print("");
    }
}
