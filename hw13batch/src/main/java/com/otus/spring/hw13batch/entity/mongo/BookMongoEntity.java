package com.otus.spring.hw13batch.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("books")
@NoArgsConstructor
@AllArgsConstructor
public class BookMongoEntity {
    private String id;
    private String authorId;
    private String genreId;
    private String name;
    private Integer year;
}
