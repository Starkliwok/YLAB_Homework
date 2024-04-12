package com.Y_LAB.homework.roles;

import com.Y_LAB.homework.exception.auth.UserDoesNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    private Admin admin;
    private User user;

    @BeforeEach
    void init() {
        user = new User("username", "password");
        User.addUserToUserSet(user);
        admin = new Admin("admin", "admin");
        User.addUserToUserSet(admin);
    }

    @Test
    void updateUserName() throws UserDoesNotExistsException {
        String username = "username";
        String newUsername = "newUsername";

        admin.updateUserName(user, newUsername);

        assertFalse(User.getAllReservedUsernamesAndPasswords().containsKey(username));
        assertEquals(user.getUsername(), User.getUserFromUserSet(user).getUsername());
    }

    @Test
    void updateUserPassword() throws UserDoesNotExistsException {
        String password = "password";
        String newPassword = "newPassword";

        admin.updateUserPassword(user, newPassword);

        assertEquals(User.getAllReservedUsernamesAndPasswords().get(user.getUsername()), newPassword);
        assertNotEquals(User.getAllReservedUsernamesAndPasswords().get(user.getUsername()), password);
        assertEquals(user.getPassword(), User.getUserFromUserSet(user).getPassword());
    }

    @Test
    void deleteUser() {
        assertTrue(User.getUserSet().contains(user));

        admin.deleteUser(user);

        assertFalse(User.getUserSet().contains(user));
    }
}