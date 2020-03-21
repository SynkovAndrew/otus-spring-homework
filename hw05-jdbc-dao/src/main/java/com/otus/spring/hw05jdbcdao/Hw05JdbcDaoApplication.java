package com.otus.spring.hw05jdbcdao;

import com.otus.spring.hw05jdbcdao.dao.AuthorDao;
import com.otus.spring.hw05jdbcdao.dao.BookDao;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class Hw05JdbcDaoApplication {
    private final AuthorDao authorDao;

    public static void main(String[] args) {
        SpringApplication.run(Hw05JdbcDaoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        authorDao.findAll().forEach(System.out::println);
    }
}
