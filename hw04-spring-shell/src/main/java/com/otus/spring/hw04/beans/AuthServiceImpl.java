package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.configuration.AuthHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthHolder authHolder;
    private boolean isLoggedIn;

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public boolean login(final String username, final String password) {
        final String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes());

        if (Objects.equals(authHolder.getUsername(), username) && Objects.equals(authHolder.getPassword(), md5Hex)) {
            this.isLoggedIn = true;
            log.debug("User \"{}\" has successfully logged in", username);
        } else {
            log.debug("Wrong username ot password!");
        }
        return isLoggedIn;
    }
}
