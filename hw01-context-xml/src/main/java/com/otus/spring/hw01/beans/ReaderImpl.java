package com.otus.spring.hw01.beans;

import com.opencsv.CSVReader;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl implements Reader {

    @Override
    public List<String[]> readFile(final String pathToFile) throws Exception {
        final URI uri = getClass().getClassLoader().getResource(pathToFile).toURI();
        final var lines = new ArrayList<String[]>();
        try (final java.io.Reader reader = Files.newBufferedReader(Paths.get(uri))) {
            try (final var csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }
}
