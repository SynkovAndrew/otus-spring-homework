package com.otus.spring.hw13batch.shell;

import com.otus.spring.hw13batch.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellApplication {
    private final JobLauncher jobLauncher;
    private final Job job;
    private final @Qualifier("mongoBookRepository")
    BookRepository mongoBookRepository;
    private final @Qualifier("sqlDbBookRepository")
    BookRepository sqlDbBookRepository;

    @ShellMethod(key = {"migrate", "m"}, value = "run migration")
    public void migrate() throws JobExecutionException {
        jobLauncher.run(job, new JobParameters());
    }

    @ShellMethod(key = {"load-book-from-mongo", "lbm"}, value = "load books from mongo")
    public void loadBookFromMongo() {
        mongoBookRepository.findBooks().forEach(System.out::println);
    }

    @ShellMethod(key = {"load-book-from-sql", "lbs"}, value = "load books from sql")
    public void loadBookFromSql() {
        sqlDbBookRepository.findBooks().forEach(System.out::println);
    }
}
