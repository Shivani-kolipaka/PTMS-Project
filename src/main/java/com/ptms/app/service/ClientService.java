package com.ptms.app.service;

import com.ptms.app.dao.ClientDAO;
import com.ptms.app.model.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private final ClientDAO clientDAO = new ClientDAO();

    public Client createClient(Client client) throws SQLException {

        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name is required");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Client email is required");
        }

        return clientDAO.create(client);
    }

    public Client findClientById(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        return clientDAO.findById(id);
    }

    public List<Client> findAllClients() throws SQLException {

        return clientDAO.findAll();
    }

    public boolean updateClient(Client client) throws SQLException {

        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (client.getId() <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name is required");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Client email is required");
        }

        return clientDAO.update(client);
    }

    public boolean deleteClient(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        return clientDAO.delete(id);
    }

    public List<Client> searchClients(String keyword) throws SQLException {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword is required");
        }

        return clientDAO.searchClients(keyword);
    }
}