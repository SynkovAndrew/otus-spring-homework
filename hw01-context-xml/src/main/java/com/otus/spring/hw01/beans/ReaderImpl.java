package com.otus.spring.hw01.beans;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderImpl implements Reader {

    @Override
    public List<String[]> readFile(final String pathToFile) throws IOException, URISyntaxException {
        final URI uri = getClass().getClassLoader().getResource(pathToFile).toURI();
        final var lines = new ArrayList<String[]>();
        try (final FileSystem fileSystem = FileSystems.newFileSystem(uri, getEnvironment())) {
            try (final java.io.Reader reader = Files.newBufferedReader(Paths.get(uri))) {
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

    private Map<String, String> getEnvironment() {
        final var env = new HashMap<String, String>();
        env.put("create", "true");
        return env;
    }
}
