package com.otus.spring.hw05jdbcdao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Hw05JdbcDaoApplication {
    /*    private final BookDao bookDao;*/

    public static void main(String[] args) {
        SpringApplication.run(Hw05JdbcDaoApplication.class, args);
    }

/*    @PostConstruct
    public void init() {
        bookDao.findAll().forEach(System.out::println);
    }*/
}
