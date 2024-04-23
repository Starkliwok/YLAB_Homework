package com.Y_LAB.homework.service.user;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.dao.audit.UserAuditDAOImpl;
import com.Y_LAB.homework.dao.user.UserDAOImpl;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserAuditDAOImpl userAuditDAO;

    @Mock
    private UserDAOImpl userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Вызов метода получения всех пользователей")
    void getAllUsers() {
        userService.getAllUsers();

        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Вызов метода получения пользователя по логину и паролю")
    void getUser() throws WrongUsernameAndPasswordException {
        String username = "username";
        String password = "password";

        userService.getUser(username, password);

        verify(userDAO, times(1)).getUser(username, password);
    }

    @Test
    @DisplayName("Вызов метода сохранения пользователя по логину и паролю")
    void saveUser() {
        String username = "username";
        String password = "password";

        userService.saveUser(username, password);

        verify(userDAO, times(1)).saveUser(username, password);
    }

    @Test
    @DisplayName("Вызов метода обновления данных пользователя")
    void updateUser() {
        long id = 1L;
        String username = "username";
        String password = "password";
        User user = new User(id, username, password);

        userService.updateUser(user);

        verify(userDAO, times(1)).updateUser(user);
    }

    @Test
    @DisplayName("Вызов метода удаления пользователя")
    void deleteUser() {
        long id = 1L;

        userService.deleteUser(id);

        verify(userDAO, times(1)).deleteUser(id);
    }

    @Test
    @DisplayName("Вызов метода получения всех аудитов")
    void getAllUserAudits() {
        userService.getAllUserAudits();

        verify(userAuditDAO, times(1)).getAllUserAudits();
    }

    @Test
    @DisplayName("Вызов метода получения аудита по идентификационному номеру")
    void getUserAudit() {
        long id = 1L;

        userService.getUserAudit(id);

        verify(userAuditDAO, times(1)).getUserAudit(id);
    }

    @Test
    @DisplayName("Вызов метода проверяющий существование пользователей с переданным логином")
    void isUserExist() {
        String username = "username";

        userService.isUserExist(username);

        verify(userDAO, times(1)).isUserExist(username);
    }

    @Test
    @DisplayName("Вызов метода сохранения аудита пользователя")
    void saveUserAudit() {
        long userId = 1L;
        String action = "some action";

        userService.saveUserAudit(userId, action, UserAuditResult.FAIL);

        verify(userAuditDAO, times(1)).saveUserAudit(userId, action, UserAuditResult.FAIL);
    }
}