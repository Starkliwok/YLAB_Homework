package com.Y_LAB.homework.entity.trainings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalDataTest {

    private AdditionalData additionalData;

    @BeforeEach
    void init() {
        additionalData = new AdditionalData(1L,"speed", "150", 1);
    }

    @Test
    void getName() {
        assertThat("speed").isEqualTo(additionalData.getName());
    }

    @Test
    void setName() {
        String newName = "Weight";
        assertThat(newName).isNotEqualTo(additionalData.getName());

        additionalData.setName(newName);

        assertThat(newName).isEqualTo(additionalData.getName());
    }

    @Test
    void getValue() {
        assertThat("150").isEqualTo(additionalData.getValue());
    }

    @Test
    void setValue() {
        String newValue = "200";
        assertThat(newValue).isNotEqualTo(additionalData.getValue());

        additionalData.setValue(newValue);

        assertThat(newValue).isEqualTo(additionalData.getValue());
    }

    @Test
    void getId() {
        assertThat(1L).isEqualTo(additionalData.getId());
    }

    @Test
    void getTrainingId() {
        assertThat(1).isEqualTo(additionalData.getTrainingId());
    }
}