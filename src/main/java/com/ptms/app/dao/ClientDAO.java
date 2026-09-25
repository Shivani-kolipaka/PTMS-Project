package com.ptms.app.dao;

import com.ptms.app.model.Client;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientDAO {

    private final Logger logger =
            Logger.getLogger(ClientDAO.class.getName());

    private final String addClient =
            "INSERT INTO clients " +
                    "(name, email, phone, company_name) " +
                    "VALUES (?, ?, ?, ?)";

    private final String findClientById =
            "SELECT id, name, email, phone, company_name " +
                    "FROM clients WHERE id = ?";

    private final String findAllClients =
            "SELECT id, name, email, phone, company_name " +
                    "FROM clients";

    private final String updateClient =
            "UPDATE clients SET " +
                    "name = ?, email = ?, phone = ?, company_name = ? " +
                    "WHERE id = ?";

    private final String deleteClient =
            "DELETE FROM clients WHERE id = ?";

    private final String searchClients =
            "SELECT id, name, email, phone, company_name " +
                    "FROM clients " +
                    "WHERE name LIKE ? " +
                    "OR email LIKE ? " +
                    "OR phone LIKE ? " +
                    "OR company_name LIKE ?";


    // CREATE
    public Client create(Client client) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addClient,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    client.setId(resultSet.getInt(1));
                }
            }

            logger.info("Client added");
        }

        return client;
    }


    // READ - by ID
    public Client findById(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findClientById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Client client = new Client();

                    client.setId(resultSet.getInt("id"));
                    client.setName(resultSet.getString("name"));
                    client.setEmail(resultSet.getString("email"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setCompanyName(
                            resultSet.getString("company_name"));

                    return client;
                }
            }
        }

        return null;
    }


    // READ - all
    public List<Client> findAll() throws SQLException {

        List<Client> clients = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findAllClients);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));
                client.setCompanyName(
                        resultSet.getString("company_name"));

                clients.add(client);
            }
        }

        return clients;
    }


    // UPDATE
    public boolean update(Client client) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateClient)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());
            statement.setInt(5, client.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Client updated");
            }

            return result > 0;
        }
    }


    // DELETE
    public boolean delete(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteClient)) {

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Client deleted");
            }

            return result > 0;
        }
    }


    // SEARCH
    public List<Client> searchClients(String keyword)
            throws SQLException {

        List<Client> clients = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(searchClients)) {

            String search = "%" + keyword + "%";

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);
            statement.setString(4, search);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setId(resultSet.getInt("id"));
                    client.setName(resultSet.getString("name"));
                    client.setEmail(resultSet.getString("email"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setCompanyName(
                            resultSet.getString("company_name"));

                    clients.add(client);
                }
            }
        }

        return clients;
    }
}