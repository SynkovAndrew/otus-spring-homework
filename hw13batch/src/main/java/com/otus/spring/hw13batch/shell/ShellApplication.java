package com.otus.spring.hw13batch.shell;

import com.otus.spring.hw13batch.entity.mongo.BookMongoEntity;
import com.otus.spring.hw13batch.entity.sql.BookSqlView;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellApplication {
    private final MongoOperations mongoOperations;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JobLauncher jobLauncher;
    private final Job job;

    @ShellMethod(key = {"migrate", "m"}, value = "run migration")
    public void migrate() throws JobExecutionException {
        jobLauncher.run(job, new JobParameters());
    }

    @ShellMethod(key = {"load-book-from-mongo", "lbm"}, value = "load books from mongo")
    public void loadBookFromMongo() {
        mongoOperations.findAll(BookMongoEntity.class)
                .forEach(System.out::println);
    }

    @ShellMethod(key = {"load-book-from-sql", "lbs"}, value = "load books from sql")
    public void loadBookFromSql() {
        final String sql = "SELECT b.id as book_id," +
                " b.name as book_name," +
                " b.year as book_year," +
                " g.id as genre_id," +
                " g.name as genre_name" +
                "FROM books b INNER JOIN genres g ON b.genre_id = g.id";


        jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BookSqlView.class))
                .forEach(System.out::println);
    }
}
