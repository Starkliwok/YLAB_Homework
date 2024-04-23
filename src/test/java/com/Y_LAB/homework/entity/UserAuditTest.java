package com.Y_LAB.homework.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuditTest {
    private UserAudit userAudit;

    @BeforeEach
    void init() {
        userAudit = new UserAudit(1L, LocalDateTime.now(),1000L, "some action", "SUCCESS");
    }

    @Test
    @DisplayName("Получение идентификационного номера аудита пользователя")
    void getId() {
        assertThat(1L).isEqualTo(userAudit.id());
    }

    @Test
    @DisplayName("Получение идентификационного номера пользователя")
    void getUserId() {
        assertThat(1000L).isEqualTo(userAudit.userId());
    }

    @Test
    @DisplayName("Получение действий пользователя")
    void getAction() {
        assertThat("some action").isEqualTo(userAudit.action());
    }

    @Test
    @DisplayName("Получение результата действий пользователя")
    void getUserAuditResult() {
        assertThat("SUCCESS").isEqualTo(userAudit.userAuditResult());
    }
}