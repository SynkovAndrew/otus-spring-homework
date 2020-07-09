package com.otus.spring.hw12authentication.repository;

import com.otus.spring.hw12authentication.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findAllByUsername(String username);
}
