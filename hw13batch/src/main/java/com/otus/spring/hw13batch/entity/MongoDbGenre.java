package com.otus.spring.hw13batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("genres")
@NoArgsConstructor
@AllArgsConstructor
public class MongoDbGenre {
    private String id;
    private Integer externalId;
    private String name;
}
