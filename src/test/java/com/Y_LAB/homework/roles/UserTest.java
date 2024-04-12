package com.Y_LAB.homework.roles;

import com.Y_LAB.homework.trainings.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private Training training;
    private Date date;

    @BeforeEach
    void init() {
        user = new User("username", "password");
        training = new Training();
        date = new Date();
        User.addUserToUserSet(user);

        Map<String, String> additionalData = new HashMap<>();
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
    void addTrainingToHistory() {
        Map<Date, List<Training>> trainingHistory = user.getTrainingHistory();
        List<Training> trainings = trainingHistory.get(date);

        assertNull(trainings);

        trainings = new ArrayList<>();

        trainings.add(training);
        trainingHistory.put(date, trainings);

        assertTrue(trainings.contains(training));
        assertTrue(trainingHistory.containsKey(date));
        assertEquals(trainingHistory.get(date), trainings);
    }

    @Test
    void deleteTraining() {
        user.addTrainingToHistory(date, training);
        Map<Date, List<Training>> trainingHistory = user.getTrainingHistory();
        assertTrue(trainingHistory.get(date).contains(training));

        user.deleteTraining(date, training);

        assertFalse(trainingHistory.get(date).contains(training));
    }

    @Test
    void getUsername() {
        User user21 = new User("username", "password");
        assertEquals("username", user21.getUsername());
    }

    @Test
    void setUsername() {
        User user11 = new User("username", "password");
        user11.setUsername("newUsername");
        assertEquals("newUsername", user11.getUsername());
    }

    @Test
    void getPassword() {
        User user213 = new User("username", "password");
        assertEquals("password", user213.getPassword());
    }

    @Test
    void setPassword() {
        User user112 = new User("username", "password");
        user112.setPassword("newPassword");
        assertEquals("newPassword", user112.getPassword());
    }

    @Test
    void getTrainingHistory() {
        Map<Date, List<Training>> trainingHistory = Map.copyOf(user.getTrainingHistory());
        user.addTrainingToHistory(date, training);
        assertNotEquals(trainingHistory, user.getTrainingHistory());
    }
}