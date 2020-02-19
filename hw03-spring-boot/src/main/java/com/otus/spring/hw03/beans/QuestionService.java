package com.otus.spring.hw03.beans;

import java.util.Map;

public interface QuestionService {
    Map<String, String> getQuestionAnswerMap();

    void loadQuestions() throws Exception;
}
