package com.otus.spring.hw13batch.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqlDbBook {
    private Integer id;
    private Integer genreId;
    private String name;
    private Integer year;
}
