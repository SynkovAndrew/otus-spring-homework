package com.otus.spring.hw12authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Id
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "password", nullable = false, length = 500)
    private String password;
}
