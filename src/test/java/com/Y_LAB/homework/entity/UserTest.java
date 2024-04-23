package com.Y_LAB.homework.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void init() {
        user = new User(1L, "test", "password");
    }

    @Test
    @DisplayName("Получение идентификационного номера пользователя")
    void getId() {
        assertThat(1L).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Получение логина пользователя")
    void getUsername() {
        assertThat("test").isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("Изменение логина пользователя и проверка на обновление информации")
    void setUsername() {
        assertThat("test").isEqualTo(user.getUsername());
        user.setUsername("test2");

        assertThat("test2").isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("Получение пароля пользователя")
    void getPassword() {
        assertThat("password").isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("Изменение пароля пользователя и проверка на обновление информации")
    void setPassword() {
        assertThat("password").isEqualTo(user.getPassword());
        user.setPassword("password2");

        assertThat("password2").isEqualTo(user.getPassword());
    }
}