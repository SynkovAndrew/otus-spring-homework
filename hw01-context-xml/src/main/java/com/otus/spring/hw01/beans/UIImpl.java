package com.otus.spring.hw01.beans;

import java.util.Map;

public class UIImpl {
    private QuestionService questionService;

    public UIImpl(final QuestionService questionService) {
        this.questionService = questionService;
    }

    public void run() {
        final Map<String, String> questionAnswerMap = questionService.getQuestionAnswerMap();
    }
}
