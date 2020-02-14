package com.otus.spring.hw01.beans;

import java.util.Map;

public interface QuestionService {
    Map<String, String> getQuestionAnswerMap();

    void loadQuestions();
}
