package com.otus.spring.hw01.beans;

import java.util.List;

public interface Reader {

    List<String[]> readFile(String pathToFile) throws Exception;
}
