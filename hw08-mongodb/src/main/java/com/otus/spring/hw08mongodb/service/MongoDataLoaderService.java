package com.otus.spring.hw08mongodb.service;

import com.otus.spring.hw08mongodb.configuration.AbstractMongoDataLoader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class MongoDataLoaderService extends AbstractMongoDataLoader {
    public MongoDataLoaderService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @PreDestroy
    private void destroy() {
        cleanData();
    }

    @PostConstruct
    private void init() {
        loadData();
    }
}
