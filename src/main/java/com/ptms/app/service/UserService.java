package com.ptms.app.service;

import com.ptms.app.dao.UserDAO;
import com.ptms.app.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO =
            new UserDAO();

    public User createUser(User user) throws SQLException {

        validateUser(user);

        user.setPassword(
                hashPassword(user.getPassword()));

        return userDAO.create(user);
    }

    public User findUserById(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID");
        }

        return userDAO.findById(id);
    }

    public List<User> findAllUsers() throws SQLException {

        return userDAO.findAll();
    }

    public boolean updateUser(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null");
        }

        if (user.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID");
        }

        validateUser(user);

        user.setPassword(
                hashPassword(user.getPassword()));

        return userDAO.update(user);
    }

    public boolean deleteUser(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID");
        }

        return userDAO.delete(id);
    }

    public boolean deactivateUser(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID");
        }

        return userDAO.deactivate(id);
    }

    public List<User> searchUsers(String keyword)
            throws SQLException {

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Search keyword is required");
        }

        return userDAO.searchUsers(keyword);
    }

    public User login(String username, String password)
            throws SQLException {

        if (username == null ||
                username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username is required");
        }

        if (password == null ||
                password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Password is required");
        }

        String hashedPassword =
                hashPassword(password);

        List<User> users =
                userDAO.findAll();

        for (User user : users) {

            if (user.isActive()
                    && user.getUsername().equals(username)
                    && user.getPassword()
                    .equals(hashedPassword)) {

                return user;
            }
        }

        return null;
    }

    private void validateUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null");
        }

        if (user.getFirstName() == null ||
                user.getFirstName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "First name is required");
        }

        if (user.getLastName() == null ||
                user.getLastName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Last name is required");
        }

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username is required");
        }

        if (user.getEmail() == null ||
                user.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Email is required");
        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Password is required");
        }

        if (user.getRoleName() == null ||
                user.getRoleName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Role is required");
        }
    }

    private String hashPassword(String password) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            password.getBytes(
                                    StandardCharsets.UTF_8));

            StringBuilder result =
                    new StringBuilder();

            for (byte value : hash) {

                result.append(
                        String.format("%02x", value));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "Password hashing failed", e);
        }
    }
}