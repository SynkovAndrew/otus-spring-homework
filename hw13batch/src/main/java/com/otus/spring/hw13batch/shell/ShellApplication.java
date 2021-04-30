package com.otus.spring.hw13batch.shell;

import com.otus.spring.hw13batch.entity.MongoDbBook;
import com.otus.spring.hw13batch.entity.SqlDbBook;
import lombok.RequiredArgsConstructor;
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

    @ShellMethod(key = {"migrate", "m"}, value = "run migration")
    public void migrate() {

    }

    @ShellMethod(key = {"load-book-from-mongo", "lbm"}, value = "load books from mongo")
    public void loadBookFromMongo() {
        mongoOperations.findAll(MongoDbBook.class)
                .forEach(System.out::println);
    }

    @ShellMethod(key = {"load-book-from-sql", "lbs"}, value = "load books from sql")
    public void loadBookFromSql() {
        jdbcTemplate.query("SELECT * FROM books", BeanPropertyRowMapper.newInstance(SqlDbBook.class))
                .forEach(System.out::println);
    }
}
