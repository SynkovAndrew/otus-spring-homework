package com.otus.spring.hw12authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Builder
@Table(name = "roles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Override
    public String getAuthority() {
        return role;
    }
}
