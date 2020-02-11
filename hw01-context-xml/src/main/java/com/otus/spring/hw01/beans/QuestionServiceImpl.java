package com.otus.spring.hw01.beans;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final Map<String, String> questionAnswerMap;
    private final Reader reader;
    @Setter
    private String pathToFile;

    public QuestionServiceImpl(final Reader reader) {
        this.reader = reader;
        this.questionAnswerMap = new HashMap<>();
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    public void loadQuestions() {
        try {
            reader.readFile(pathToFile).forEach(line -> questionAnswerMap.put(line[0], line[1]));
        } catch (Exception e) {
            log.error("Failed to load questions!");
        }
    }
}
