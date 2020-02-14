package com.otus.spring.hw01.beans;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI implements UI {
    private final Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void print(String message) {
        System.out.println();
        System.out.println(message);
    }

    @Override
    public List<String> read() {
        final String input = scanner.nextLine();
        final String[] parts = input.split(" ");
        return Arrays.asList(parts);
    }
}
