package com.otus.spring.hw04.shell;

import com.otus.spring.hw04.beans.AuthService;
import com.otus.spring.hw04.beans.EasyMessageSource;
import com.otus.spring.hw04.beans.QuestionService;
import com.otus.spring.hw04.beans.UI;
import com.otus.spring.hw04.configuration.SettingsHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Objects;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellApplication {
    private final AuthService authService;
    private final EasyMessageSource messageSource;
    private final QuestionService questionService;
    private final SettingsHolder settingsHolder;
    private final UI ui;
    private int score;

    private Availability isStartCommandAvailable() {
        return authService.isLoggedIn() ? available() : unavailable(messageSource.get("not.logged.in.error.message"));
    }

    @ShellMethod(key = {"login", "l"}, value = "enter into system as user")
    public void login(final @ShellOption({"username", "u"}) String username,
                      final @ShellOption({"password", "p"}) String password) {
        if (authService.login(username, password)) {
            ui.print(messageSource.get("successful.login.message"));
        } else {
            ui.print(messageSource.get("failed.to.login.message"));
        }
    }

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
            ui.print(messageSource.get("cant.load.questions.error.message"));
            log.error("Failed to load questions:", e);
        }
    }

    @ShellMethod(key = {"start", "s"}, value = "start quiz")
    @ShellMethodAvailability(value = "isStartCommandAvailable")
    public void start() {
        run();
    }
}
