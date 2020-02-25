package com.otus.spring.hw04.beans;

import java.util.List;

public interface Reader {

    List<String[]> readFile(String pathToFile) throws Exception;
}
