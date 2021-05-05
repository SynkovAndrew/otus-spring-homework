package com.otus.spring.hw13batch.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("authors")
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMongoEntity {
    private String id;
    private String name;
}
