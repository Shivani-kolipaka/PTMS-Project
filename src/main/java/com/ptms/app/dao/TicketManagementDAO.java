package com.ptms.app.dao;

import com.ptms.app.model.Ticket;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TicketManagementDAO {

    private final Logger logger =
            Logger.getLogger(TicketManagementDAO.class.getName());

    private final String addTicket =
            "INSERT INTO ticket_management " +
                    "(project_id, title, description, priority, deadline, " +
                    "assigned_to, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String findTicketById =
            "SELECT id, project_id, title, description, priority, " +
                    "deadline, assigned_to, status, created_at " +
                    "FROM ticket_management WHERE id = ?";

    private final String findAllTickets =
            "SELECT id, project_id, title, description, priority, " +
                    "deadline, assigned_to, status, created_at " +
                    "FROM ticket_management";

    private final String findTicketsByProject =
            "SELECT id, project_id, title, description, priority, " +
                    "deadline, assigned_to, status, created_at " +
                    "FROM ticket_management WHERE project_id = ?";

    private final String updateTicket =
            "UPDATE ticket_management SET " +
                    "project_id = ?, title = ?, description = ?, priority = ?, " +
                    "deadline = ?, assigned_to = ?, status = ? " +
                    "WHERE id = ?";

    private final String deleteTicket =
            "DELETE FROM ticket_management WHERE id = ?";

    private final String searchTickets =
            "SELECT id, project_id, title, description, priority, " +
                    "deadline, assigned_to, status, created_at " +
                    "FROM ticket_management " +
                    "WHERE title LIKE ? OR priority LIKE ? OR status LIKE ?";


    // CREATE
    public Ticket create(Ticket ticket) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addTicket,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());

            if (ticket.getDeadline() != null) {
                statement.setDate(
                        5,
                        Date.valueOf(ticket.getDeadline()));
            } else {
                statement.setNull(5, Types.DATE);
            }

            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    ticket.setId(resultSet.getInt(1));
                }
            }

            logger.info("Ticket added");
        }

        return ticket;
    }


    // READ - by ID
    public Ticket findById(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findTicketById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTicket(resultSet);
                }
            }
        }

        return null;
    }


    // READ - all
    public List<Ticket> findAll() throws SQLException {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findAllTickets);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tickets.add(mapTicket(resultSet));
            }
        }

        return tickets;
    }


    // READ - by project
    public List<Ticket> findByProjectId(int projectId)
            throws SQLException {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findTicketsByProject)) {

            statement.setInt(1, projectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }
        }

        return tickets;
    }


    // UPDATE
    public boolean update(Ticket ticket) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateTicket)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());

            if (ticket.getDeadline() != null) {
                statement.setDate(
                        5,
                        Date.valueOf(ticket.getDeadline()));
            } else {
                statement.setNull(5, Types.DATE);
            }

            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());
            statement.setInt(8, ticket.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Ticket updated");
            }

            return result > 0;
        }
    }


    // DELETE
    public boolean delete(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteTicket)) {

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Ticket deleted");
            }

            return result > 0;
        }
    }


    // SEARCH
    public List<Ticket> searchTickets(String keyword)
            throws SQLException {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(searchTickets)) {

            String search = "%" + keyword + "%";

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }
        }

        return tickets;
    }


    private Ticket mapTicket(ResultSet resultSet)
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setId(resultSet.getInt("id"));
        ticket.setProjectId(resultSet.getInt("project_id"));
        ticket.setTitle(resultSet.getString("title"));
        ticket.setDescription(
                resultSet.getString("description"));
        ticket.setPriority(
                resultSet.getString("priority"));

        Date deadline =
                resultSet.getDate("deadline");

        if (deadline != null) {
            ticket.setDeadline(
                    deadline.toLocalDate());
        }

        ticket.setAssignedTo(
                resultSet.getInt("assigned_to"));

        ticket.setStatus(
                resultSet.getString("status"));

        ticket.setCreatedAt(
                resultSet.getTimestamp("created_at"));

        return ticket;
    }
}