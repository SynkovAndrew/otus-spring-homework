package com.otus.spring.hw01.beans;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl implements Reader {

    @Override
    public List<String[]> readFile(final String pathToFile) throws IOException, URISyntaxException {
        final java.io.Reader reader = Files.newBufferedReader(
                Paths.get(ClassLoader.getSystemResource(pathToFile).toURI()));
        final var csvReader = new CSVReader(reader);
        final var lines = new ArrayList<String[]>();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            lines.add(line);
        }
        reader.close();
        csvReader.close();
        return lines;
    }
}
