package com.otus.spring.hw03.beans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.otus.spring.hw03.configuration.Constant.SPRING_PROFILE_TEST;

@Service
@Profile("!" + SPRING_PROFILE_TEST)
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
