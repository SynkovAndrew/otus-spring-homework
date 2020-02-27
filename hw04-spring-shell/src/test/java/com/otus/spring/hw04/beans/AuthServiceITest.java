package com.otus.spring.hw04.beans;

import com.otus.spring.hw04.configuration.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.otus.spring.hw04.configuration.Constant.SPRING_PROFILE_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfiguration.class})
@ActiveProfiles(SPRING_PROFILE_TEST)
public class AuthServiceITest {
    @Autowired
    private AuthService authService;

    @Test
    @DirtiesContext
    public void loginTest_successful() {
        assertThat(authService.isLoggedIn()).isFalse();
        assertThat(authService.login("test", "test")).isTrue();
        assertThat(authService.isLoggedIn()).isTrue();
    }

    @Test
    @DirtiesContext
    public void loginTest_wrongPassword() {
        assertThat(authService.isLoggedIn()).isFalse();
        assertThat(authService.login("test", "wrong")).isFalse();
        assertThat(authService.isLoggedIn()).isFalse();
    }

    @Test
    @DirtiesContext
    public void loginTest_wrongUsername() {
        assertThat(authService.isLoggedIn()).isFalse();
        assertThat(authService.login("wrong", "test")).isFalse();
        assertThat(authService.isLoggedIn()).isFalse();
    }
}
