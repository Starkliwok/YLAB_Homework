package com.Y_LAB.homework.in.auth_panel;

import com.Y_LAB.homework.roles.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationPanelTest {

    @Test
    void createNewUser() {
        assertFalse(User.getAllReservedUsernamesAndPasswords().containsKey("test"));

        RegistrationPanel.createNewUser("test", "test");

        assertTrue(User.getAllReservedUsernamesAndPasswords().containsKey("test"));

        User.deleteUserFromUserSet(new User("test", "test"));
    }
}