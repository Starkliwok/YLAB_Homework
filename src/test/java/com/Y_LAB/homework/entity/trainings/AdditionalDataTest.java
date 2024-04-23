package com.Y_LAB.homework.entity.trainings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalDataTest {

    private AdditionalData additionalData;

    @BeforeEach
    void init() {
        additionalData = new AdditionalData(1L,"speed", "150", 1);
    }

    @Test
    @DisplayName("Получение имени дополнительной информации")
    void getName() {
        assertThat("speed").isEqualTo(additionalData.getName());
    }

    @Test
    @DisplayName("Изменение имени дополнительной информации и проверка на обновление имени")
    void setName() {
        String newName = "Weight";
        assertThat(newName).isNotEqualTo(additionalData.getName());

        additionalData.setName(newName);

        assertThat(newName).isEqualTo(additionalData.getName());
    }

    @Test
    @DisplayName("Получение значения дополнительной информации")
    void getValue() {
        assertThat("150").isEqualTo(additionalData.getValue());
    }

    @Test
    @DisplayName("Изменение значения дополнительной информации и проверка на обновление значения")
    void setValue() {
        String newValue = "200";
        assertThat(newValue).isNotEqualTo(additionalData.getValue());

        additionalData.setValue(newValue);

        assertThat(newValue).isEqualTo(additionalData.getValue());
    }

    @Test
    @DisplayName("Получение идентификационного номера дополнительной информации")
    void getId() {
        assertThat(1L).isEqualTo(additionalData.getId());
    }

    @Test
    @DisplayName("Получение идентификационного номера тренировки дополнительной информации")
    void getTrainingId() {
        assertThat(1).isEqualTo(additionalData.getTrainingId());
    }
}