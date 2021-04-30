package com.otus.spring.hw13batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongoDbAuthor {
    private String id;
    private Integer externalId;
    private String name;
}
