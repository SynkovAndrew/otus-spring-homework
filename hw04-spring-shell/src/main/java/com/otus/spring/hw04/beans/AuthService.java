package com.otus.spring.hw04.beans;

public interface AuthService {
    boolean isLoggedIn();

    boolean login(String username, String password);
}
