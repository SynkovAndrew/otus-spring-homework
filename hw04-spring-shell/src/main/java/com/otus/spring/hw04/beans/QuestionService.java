package com.otus.spring.hw04.beans;

import java.util.Map;

public interface QuestionService {
    Map<String, String> getQuestionAnswerMap();

    void loadQuestions() throws Exception;
}
