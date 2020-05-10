package com.otus.spring.hw08mongodb.ui;

public abstract class ConsoleUI {
    protected void show(final Runnable runnable) {
        System.out.println();
        runnable.run();
        System.out.println();
    }
}
