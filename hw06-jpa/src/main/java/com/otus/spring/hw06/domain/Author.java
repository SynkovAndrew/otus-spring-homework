package com.otus.spring.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Table(name = "authors")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
}
