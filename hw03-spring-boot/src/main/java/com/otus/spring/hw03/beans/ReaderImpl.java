package com.otus.spring.hw03.beans;

import com.opencsv.CSVReader;
import com.otus.spring.hw03.aspect.Measurable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderImpl implements Reader {
    @Override
    @Measurable
    public List<String[]> readFile(final String pathToFile) throws Exception {
        final var lines = new ArrayList<String[]>();
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToFile)) {
            try (final java.io.Reader reader = new InputStreamReader(inputStream)) {
                try (final var csvReader = new CSVReader(reader)) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        lines.add(line);
                    }
                }
            }
        }
        return lines;
    }
}
