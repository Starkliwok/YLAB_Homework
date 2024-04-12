package com.Y_LAB.homework.trainings;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void addAdditionalData() {
        assertTrue(additionalData.containsKey("field1")
                && training.getAdditionalDataMap().containsKey("field1")
                && additionalData.get("field1").equals(training.getAdditionalDataMap().get("field1")));

        training.addAdditionalData(new AdditionalData("field4", "value4"));

        assertTrue(additionalData.containsKey("field4")
                && training.getAdditionalDataMap().containsKey("field4")
                && additionalData.get("field4").equals(training.getAdditionalDataMap().get("field4")));
    }

    @Test
    void getName() {
        Training training1 = new Training();
        training1.setName("aerobic");
        assertEquals("aerobic", training1.getName());
    }

    @Test
    void setName() {
        assertEquals("name", training.getName());
        training.setName("new name");
        assertEquals("new name", training.getName());
    }

    @Test
    void getCaloriesSpent() {
        Training training1 = new Training();
        training1.setCaloriesSpent(150);
        assertEquals(150, training1.getCaloriesSpent());
    }

    @Test
    void setCaloriesSpent() {
        assertEquals(1500, training.getCaloriesSpent());
        training.setCaloriesSpent(2000);
        assertEquals(2000, training.getCaloriesSpent());
    }

    @Test
    void getDurationInMinutes() {
        Training training1 = new Training();
        training1.setDurationInMinutes(100);
        assertEquals(100, training1.getDurationInMinutes());
    }

    @Test
    void setDurationInMinutes() {
        assertEquals(100, training.getDurationInMinutes());
        training.setDurationInMinutes(200);
        assertEquals(200, training.getDurationInMinutes());
    }

    @Test
    void getAdditionalDataMap() {
        Training training1 = new Training();
        additionalData.put("some key", "some value");
        training1.setAdditionalDataMap(additionalData);
        assertEquals(additionalData, training1.getAdditionalDataMap());
        additionalData.remove("some key");
    }

    @Test
    void setAdditionalDataMap() {
        Map<String, String> expected = new HashMap<>();
        expected.put("key", "value");
        assertNotEquals(expected, training.getAdditionalDataMap());
        training.setAdditionalDataMap(expected);
        assertEquals(expected, training.getAdditionalDataMap());
    }

    @Test
    void getType() {
        Training training1 = new Training();
        training1.setType("Cardio");
        assertEquals("Cardio", training1.getType());
    }

    @Test
    void setType() {
        assertEquals("bla", training.getType());
        training.setType("ne bla");
        assertEquals("ne bla", training.getType());
    }
}