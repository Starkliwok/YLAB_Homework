package com.Y_LAB.homework.trainings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalDataTest {
    private AdditionalData additionalData;

    @BeforeEach
    void init() {
        additionalData = new AdditionalData("speed", "150");
    }

    @Test
    void getName() {
        assertEquals("speed", additionalData.getName());
    }

    @Test
    void setName() {
        String newName = "Weight";
        assertNotEquals(newName, additionalData.getName());
        additionalData.setName(newName);
        assertEquals(newName, additionalData.getName());
    }

    @Test
    void getValue() {
        assertEquals("150", additionalData.getValue());
    }

    @Test
    void setValue() {
        String newValue = "200";
        assertNotEquals(newValue, additionalData.getValue());
        additionalData.setValue(newValue);
        assertEquals(newValue, additionalData.getValue());
    }
}