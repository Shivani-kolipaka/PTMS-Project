package com.ptms.app.service;

import com.ptms.app.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;



public class UserServiceTest {

    private final UserService userService =
            new UserService();

    @Test
    void createUserShouldRejectNullUser() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(null)
        );
    }

    @Test
    void createUserShouldRejectMissingUsername()
            throws SQLException {

        User user = new User();

        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("");
        user.setEmail("test@example.com");
        user.setPassword("test123");
        user.setRoleName("TEAM_MEMBER");

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(user)
        );
    }

    @Test
    void findUserShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.findUserById(0)
        );
    }

    @Test
    void deleteUserShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(0)
        );
    }

    @Test
    void deactivateUserShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.deactivateUser(0)
        );
    }

    @Test
    void searchUsersShouldRejectEmptyKeyword()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.searchUsers("")
        );
    }

    @Test
    void loginShouldRejectEmptyUsername()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.login("", "test123")
        );
    }

    @Test
    void loginShouldRejectEmptyPassword()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.login("admin", "")
        );
    }
}
