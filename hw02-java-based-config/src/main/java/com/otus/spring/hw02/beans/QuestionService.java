package com.otus.spring.hw02.beans;

import java.util.Map;

public interface QuestionService {
    Map<String, String> getQuestionAnswerMap();

    void loadQuestions();
}
