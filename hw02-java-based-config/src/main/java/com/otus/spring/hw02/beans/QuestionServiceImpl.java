package com.otus.spring.hw02.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    private final String pathToFile;
    private final Map<String, String> questionAnswerMap;
    private final Reader reader;

    public QuestionServiceImpl(final Reader reader,
                               final @Value("${path.to.question.file}") String pathToFile) {
        this.reader = reader;
        this.pathToFile = pathToFile;
        this.questionAnswerMap = new HashMap<>();
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    @PostConstruct
    public void init() {
        loadQuestions();
    }

    public void loadQuestions() {
        try {
            reader.readFile(pathToFile).forEach(line -> questionAnswerMap.put(line[0], line[1]));
        } catch (Exception e) {
            log.error("Failed to load questions!");
        }
    }
}
