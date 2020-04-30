package com.otus.spring.hw06.ui;

public abstract class ConsoleUI {
    protected void show(final Runnable runnable) {
        System.out.println();
        runnable.run();
        System.out.println();
    }
}
