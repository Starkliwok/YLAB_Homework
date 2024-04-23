package com.Y_LAB.homework.entity.trainings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingTest {

    private Training training;
    private Map<String, String> additionalData;

    @BeforeEach
    void init() {
        training = new Training();

        additionalData = new HashMap<>();
        additionalData.put("field1", "value1");
        additionalData.put("field2", "value2");
        additionalData.put("field3", "value3");

        training.setType("bla");
        training.setName("name");
        training.setCaloriesSpent(1500);
        training.setDurationInMinutes(100);
        training.setAdditionalDataMap(additionalData);
        training.setDate(new Date(1220227200));
        training.setUserId(10);
    }

    @Test
    @DisplayName("Получение имени тренировки")
    void getName() {
        Training training1 = new Training();

        training1.setName("aerobic");

        assertThat("aerobic").isEqualTo(training1.getName());
    }

    @Test
    @DisplayName("Изменение значения имени тренировки и проверка на обновление имени")
    void setName() {
        assertThat("name").isEqualTo(training.getName());
        training.setName("new name");

        assertThat("new name").isEqualTo(training.getName());
    }

    @Test
    @DisplayName("Получение количества потраченных калорий во время тренировки")
    void getCaloriesSpent() {
        Training training1 = new Training();
        training1.setCaloriesSpent(150);

        assertThat(150).isEqualTo(training1.getCaloriesSpent());
    }

    @Test
    @DisplayName("Изменение количества потраченных калорий и проверка на обновление информации")
    void setCaloriesSpent() {
        assertThat(1500).isEqualTo(training.getCaloriesSpent());
        training.setCaloriesSpent(2000);

        assertThat(2000).isEqualTo(training.getCaloriesSpent());
    }

    @Test
    @DisplayName("Получение длительности времени тренировки")
    void getDurationInMinutes() {
        Training training1 = new Training();
        training1.setDurationInMinutes(100.0);

        assertThat(100.0).isEqualTo(training1.getDurationInMinutes());
    }

    @Test
    @DisplayName("Изменение длительности времени тренировки и проверка на обновление информации")
    void setDurationInMinutes() {
        assertThat(100.0).isEqualTo(training.getDurationInMinutes());
        training.setDurationInMinutes(200);

        assertThat(200.0).isEqualTo(training.getDurationInMinutes());
    }

    @Test
    @DisplayName("Получение дополнительной информации тренировки")
    void getAdditionalDataMap() {
        Training training1 = new Training();
        additionalData.put("some key", "some value");
        training1.setAdditionalDataMap(additionalData);

        assertThat(additionalData).isEqualTo(training1.getAdditionalDataMap());
        additionalData.remove("some key");
    }

    @Test
    @DisplayName("Изменение дополнительной информации тренировки и проверка на обновление информации")
    void setAdditionalDataMap() {
        Map<String, String> expected = new HashMap<>();
        expected.put("key", "value");

        assertThat(expected).isNotEqualTo(training.getAdditionalDataMap());
        training.setAdditionalDataMap(expected);

        assertThat(expected).isEqualTo(training.getAdditionalDataMap());
    }

    @Test
    @DisplayName("Получение типа тренировки")
    void getType() {
        Training training1 = new Training();
        training1.setType("Cardio");

        assertThat("Cardio").isEqualTo(training1.getType());
    }

    @Test
    @DisplayName("Изменение типа тренировки и проверка на обновление информации")
    void setType() {
        assertThat("bla").isEqualTo(training.getType());
        training.setType("ne bla");

        assertThat("ne bla").isEqualTo(training.getType());
    }

    @Test
    @DisplayName("Получение идентификационного номера тренировки")
    void getId() {
        Training training1 = new Training(1L, "some", "name", new Date(1220227200),
                0, 1, 0);

        assertThat(1L).isEqualTo(training1.getId());
    }

    @Test
    @DisplayName("Получение даты тренировки")
    void getDate() {
        Training training1 = new Training();
        training1.setDate(new Date(1220227200));

        assertThat(new Date(1220227200)).isEqualTo(training1.getDate());
    }

    @Test
    @DisplayName("Изменение даты тренировки и проверка на обновление информации")
    void setDate() {
        assertThat(new Date(1220227200)).isEqualTo(training.getDate());
        training.setDate(new Date(1220327200));

        assertThat(new Date(1220327200)).isEqualTo(training.getDate());
    }

    @Test
    @DisplayName("Получение идентификационного номера владельца тренировки")
    void getUserId() {
        Training training1 = new Training();
        training1.setUserId(100);

        assertThat(100).isEqualTo(training1.getUserId());
    }

    @Test
    @DisplayName("Изменение идентификационного номера владельца тренировки и проверка на обновление информации")
    void setUserId() {
        assertThat(10).isEqualTo(training.getUserId());
        training.setUserId(12);

        assertThat(12).isEqualTo(training.getUserId());
    }
}