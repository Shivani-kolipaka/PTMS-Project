package com.ptms.app.dao;

import com.ptms.app.model.User;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDAO {

    private final Logger logger =
            Logger.getLogger(UserDAO.class.getName());

    private final String addUser =
            "INSERT INTO users " +
                    "(first_name, last_name, username, email, password, role_name, " +
                    "date_of_birth, mobile_number, gender) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String findUserById =
            "SELECT id, first_name, last_name, username, email, " +
                    "password, role_name, date_of_birth, mobile_number, gender " +
                    "FROM users WHERE id = ?";

    private final String findAllUsers =
            "SELECT id, first_name, last_name, username, email, " +
                    "password, role_name, date_of_birth, mobile_number, gender " +
                    "FROM users";

    private final String updateUser =
            "UPDATE users SET " +
                    "first_name = ?, last_name = ?, username = ?, email = ?, " +
                    "password = ?, role_name = ?, date_of_birth = ?, " +
                    "mobile_number = ?, gender = ? " +
                    "WHERE id = ?";

    private final String deleteUser =
            "DELETE FROM users WHERE id = ?";

    private final String searchUsers =
            "SELECT id, first_name, last_name, username, email, " +
                    "password, role_name, date_of_birth, mobile_number, gender " +
                    "FROM users " +
                    "WHERE first_name LIKE ? " +
                    "OR last_name LIKE ? " +
                    "OR username LIKE ? " +
                    "OR email LIKE ?";


    // CREATE
    public User create(User user) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addUser,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRoleName());

            if (user.getDateOfBirth() != null) {
                statement.setDate(
                        7,
                        Date.valueOf(user.getDateOfBirth()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, user.getMobileNumber());
            statement.setString(9, user.getGender());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }

            logger.info("User added");
        }

        return user;
    }


    // READ - by ID
    public User findById(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findUserById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        }

        return null;
    }


    // READ - all
    public List<User> findAll() throws SQLException {

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findAllUsers);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        }

        return users;
    }


    // UPDATE
    public boolean update(User user) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateUser)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRoleName());

            if (user.getDateOfBirth() != null) {
                statement.setDate(
                        7,
                        Date.valueOf(user.getDateOfBirth()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, user.getMobileNumber());
            statement.setString(9, user.getGender());
            statement.setInt(10, user.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("User updated");
            }

            return result > 0;
        }
    }


    // DELETE
    public boolean delete(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteUser)) {

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("User deleted");
            }

            return result > 0;
        }
    }


    // SEARCH
    public List<User> searchUsers(String keyword) throws SQLException {

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(searchUsers)) {

            String search = "%" + keyword + "%";

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);
            statement.setString(4, search);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }
        }

        return users;
    }


    private User mapUser(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRoleName(resultSet.getString("role_name"));

        Date dateOfBirth =
                resultSet.getDate("date_of_birth");

        if (dateOfBirth != null) {
            user.setDateOfBirth(
                    dateOfBirth.toLocalDate());
        }

        user.setMobileNumber(
                resultSet.getString("mobile_number"));

        user.setGender(
                resultSet.getString("gender"));

        return user;
    }
}