package com.otus.spring.hw03.beans;

import java.util.List;

public interface Reader {

    List<String[]> readFile(String pathToFile) throws Exception;
}
