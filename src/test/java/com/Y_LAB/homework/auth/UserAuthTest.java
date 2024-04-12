package com.Y_LAB.homework.auth;

import com.Y_LAB.homework.exception.auth.UserDoesNotExistsException;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.roles.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthTest {
    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user = new User("test user", "test user");
        user2 = new User("test user2", "some password");
        user3 = new User("432432432432", "543543543");

        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);
    }

    @AfterEach
    public void endMethod() {
        User.deleteUserFromUserSet(user);
        User.deleteUserFromUserSet(user2);
        User.deleteUserFromUserSet(user3);
    }

    @Test
    void addUserToUserSet() {
        Set<User> actual = User.getUserSet();
        Set<User> expected = new HashSet<>();

        expected.add(user);
        expected.add(user2);
        expected.add(user3);

        assertEquals(expected, actual);
    }
    @Test
    void addUserToReservedUsernamesAndPasswordsList() {
        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);

        Map<String, String> actual = User.getAllReservedUsernamesAndPasswords();
        Map<String, String> expected = new HashMap<>();

        expected.put(user.getUsername(), user.getPassword());
        expected.put(user2.getUsername(), user2.getPassword());
        expected.put(user3.getUsername(), user3.getPassword());

        assertEquals(expected, actual);
    }

    @Test
    void deleteUserFromUserSet() {
        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);

        Set<User> actual = User.getUserSet();
        Set<User> expected = new HashSet<>();

        expected.add(user);
        expected.add(user2);
        expected.add(user3);

        User.deleteUserFromUserSet(user);
        expected.remove(user);

        assertEquals(expected, actual);
    }

    @Test
    void deleteUserFromReservedUsernamesAndPasswordsList() {
        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);

        Map<String, String> actual = User.getAllReservedUsernamesAndPasswords();
        Map<String, String> expected = new HashMap<>();

        expected.put(user.getUsername(), user.getPassword());
        expected.put(user2.getUsername(), user2.getPassword());
        expected.put(user3.getUsername(), user3.getPassword());

        User.deleteUserFromUserSet(user);
        expected.remove(user.getUsername());

        assertEquals(expected, actual);
    }

    @Test
    void getUserWithUserObject() {
        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);

        User fakeuser = new User("test", "test");

        List<User> expected = new ArrayList<>();
        List<User> actual = new ArrayList<>();
        actual.add(user);
        actual.add(user2);
        actual.add(user3);
        try {
            User actualUser = User.getUserFromUserSet(user);
            User actualUser2 = User.getUserFromUserSet(user2);
            User actualUser3 = User.getUserFromUserSet(user3);

            expected.add(actualUser);
            expected.add(actualUser2);
            expected.add(actualUser3);

            assertFalse(User.getUserSet().contains(fakeuser));
        } catch (UserDoesNotExistsException e) {
            fail(e.getCause());
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void getUserWithUsernameAndPassword() throws WrongUsernameAndPasswordException {
        User.addUserToUserSet(user);
        User.addUserToUserSet(user2);
        User.addUserToUserSet(user3);

        List<User> expected = new ArrayList<>();
        List<User> actual = new ArrayList<>();
        actual.add(user);
        actual.add(user2);
        actual.add(user3);

        User actualUser = User.getUserFromUserSet(user.getUsername(), user.getPassword());
        User actualUser2 = User.getUserFromUserSet(user2.getUsername(), user2.getPassword());
        User actualUser3 = User.getUserFromUserSet(user3.getUsername(), user3.getPassword());

        expected.add(actualUser);
        expected.add(actualUser2);
        expected.add(actualUser3);

        assertEquals(expected, actual);
    }

    @Test
    void getAllReservedUsernamesAndPasswords() {
        User user4 = new User("bla", "bla");
        Map<String, String> usernamesAndPassword = Map.copyOf(User.getAllReservedUsernamesAndPasswords());
        User.addUserToUserSet(user4);
        assertNotEquals(usernamesAndPassword, User.getAllReservedUsernamesAndPasswords());
        User.deleteUserFromUserSet(user4);

        Map<String, String> usernamesAndPassword2 = User.getAllReservedUsernamesAndPasswords();
        User.addUserToUserSet(user4);
        assertEquals(usernamesAndPassword2, User.getAllReservedUsernamesAndPasswords());
        User.deleteUserFromUserSet(user4);
    }

    @Test
    void getUsernameSet() {
        User user4 = new User("bla", "bla");
        Set<String> usernames = Set.copyOf(User.getUsernameSet());
        User.addUserToUserSet(user4);
        assertNotEquals(usernames, User.getUsernameSet());
        User.deleteUserFromUserSet(user4);

        Set<String>  usernames2 = User.getUsernameSet();
        User.addUserToUserSet(user4);
        assertEquals(usernames2, User.getUsernameSet());
        User.deleteUserFromUserSet(user4);
    }

    @Test
    void getUserSet() {
        User user4 = new User("bla", "bla");
        Set<User> users = Set.copyOf(User.getUserSet());
        User.addUserToUserSet(user4);
        assertNotEquals(users, User.getUserSet());
        User.deleteUserFromUserSet(user4);

        Set<User>  users2 = User.getUserSet();
        User.addUserToUserSet(user4);
        assertEquals(users2, User.getUserSet());
        User.deleteUserFromUserSet(user4);
    }
}