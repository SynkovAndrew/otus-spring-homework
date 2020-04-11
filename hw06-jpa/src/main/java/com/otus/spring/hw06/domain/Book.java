package com.otus.spring.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "book")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Author author;
    @Column("genre_id")
    @OneToOne(targetEntity = "genre_id")
    private Genre genre;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "year")
    private Integer year;
}
