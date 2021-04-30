package com.otus.spring.hw13batch.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private Genre genre;
    private Author author;
    private String name;
    private Integer year;
}
