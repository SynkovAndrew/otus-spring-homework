package com.otus.spring.hw02.beans;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderImpl implements Reader {

    @Override
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
