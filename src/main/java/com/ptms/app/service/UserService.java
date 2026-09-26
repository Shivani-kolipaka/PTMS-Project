package com.ptms.app.service;

import com.ptms.app.dao.UserDAO;
import com.ptms.app.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User createUser(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        return userDAO.create(user);
    }

    public User findUserById(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return userDAO.findById(id);
    }

    public List<User> findAllUsers() throws SQLException {

        return userDAO.findAll();
    }

    public boolean updateUser(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        return userDAO.update(user);
    }

    public boolean deleteUser(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return userDAO.delete(id);
    }

    public List<User> searchUsers(String keyword) throws SQLException {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword is required");
        }

        return userDAO.searchUsers(keyword);
    }
}
