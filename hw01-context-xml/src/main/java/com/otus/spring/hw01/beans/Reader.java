package com.otus.spring.hw01.beans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Reader {

    List<String[]> readFile(String pathToFile) throws IOException, URISyntaxException;
}
