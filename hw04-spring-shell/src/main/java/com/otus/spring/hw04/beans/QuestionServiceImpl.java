package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.aspect.Measurable;
import com.otus.spring.hw04.configuration.SettingsHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final Map<String, String> questionAnswerMap = new HashMap<>();
    private final Reader reader;
    private final SettingsHolder settingsHolder;

    @Measurable
    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    @Measurable
    public void loadQuestions() throws Exception {
        final String pathToFile = format(settingsHolder.getPathToQuestionFile(), settingsHolder.getLanguage());
        reader.readFile(pathToFile).forEach(line -> questionAnswerMap.put(
                new String(line[0].getBytes(), UTF_8), new String(line[1].getBytes(), UTF_8)));
        log.info("{} questions have been loaded", questionAnswerMap.size());
    }
}
