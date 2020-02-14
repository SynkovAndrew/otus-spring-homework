package com.otus.spring.hw02.beans;

import java.util.List;

public interface Reader {

    List<String[]> readFile(String pathToFile) throws Exception;
}
