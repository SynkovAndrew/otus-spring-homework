package com.otus.spring.hw02.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    private final String pathToFile;
    private final Map<String, String> questionAnswerMap;
    private final Reader reader;

    public QuestionServiceImpl(final Reader reader,
                               final @Value("${path.to.question.file}") String pathToFile,
                               final @Value("${settings.language}") String language) {
        this.reader = reader;
        this.pathToFile = String.format(pathToFile, language);
        this.questionAnswerMap = new HashMap<>();
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    public void loadQuestions() throws Exception {
        reader.readFile(pathToFile).forEach(line -> questionAnswerMap.put(
                new String(line[0].getBytes(), UTF_8), new String(line[1].getBytes(), UTF_8)));
        log.info("{} questions have been loaded", questionAnswerMap.size());
    }
}
