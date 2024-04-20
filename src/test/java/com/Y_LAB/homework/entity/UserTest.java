package com.Y_LAB.homework.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void init() {
        user = new User(1L, "test", "password");
    }

    @Test
    void getId() {
        assertThat(1L).isEqualTo(user.getId());
    }

    @Test
    void getUsername() {
        assertThat("test").isEqualTo(user.getUsername());
    }

    @Test
    void setUsername() {
        assertThat("test").isEqualTo(user.getUsername());
        user.setUsername("test2");

        assertThat("test2").isEqualTo(user.getUsername());
    }

    @Test
    void getPassword() {
        assertThat("password").isEqualTo(user.getPassword());
    }

    @Test
    void setPassword() {
        assertThat("password").isEqualTo(user.getPassword());
        user.setPassword("password2");

        assertThat("password2").isEqualTo(user.getPassword());
    }
}