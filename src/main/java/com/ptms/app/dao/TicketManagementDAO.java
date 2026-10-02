package com.ptms.app.dao;

import com.ptms.app.model.Ticket;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TicketManagementDAO {

    private final Logger logger =
            Logger.getLogger(TicketManagementDAO.class.getName());

    private final String addTicket =
            "INSERT INTO ticket_management " +
                    "(project_id, title, description, priority, deadline, assigned_to, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String findTicketById =
            "SELECT id, project_id, title, description, priority, deadline, " +
                    "assigned_to, status, created_at " +
                    "FROM ticket_management WHERE id = ?";

    private final String findAllTickets =
            "SELECT id, project_id, title, description, priority, deadline, " +
                    "assigned_to, status, created_at " +
                    "FROM ticket_management";

    private final String findTicketsByProject =
            "SELECT id, project_id, title, description, priority, deadline, " +
                    "assigned_to, status, created_at " +
                    "FROM ticket_management WHERE project_id = ?";

    private final String findTicketsByAssignedUser =
            "SELECT id, project_id, title, description, priority, deadline, " +
                    "assigned_to, status, created_at " +
                    "FROM ticket_management WHERE assigned_to = ?";

    private final String searchTickets =
            "SELECT id, project_id, title, description, priority, deadline, " +
                    "assigned_to, status, created_at " +
                    "FROM ticket_management " +
                    "WHERE title LIKE ? OR description LIKE ?";

    private final String updateTicket =
            "UPDATE ticket_management SET project_id = ?, title = ?, " +
                    "description = ?, priority = ?, deadline = ?, assigned_to = ?, " +
                    "status = ? WHERE id = ?";

    private final String assignTicket =
            "UPDATE ticket_management SET assigned_to = ? WHERE id = ?";

    private final String updateStatus =
            "UPDATE ticket_management SET status = ? WHERE id = ?";

    private final String deleteTicket =
            "DELETE FROM ticket_management WHERE id = ?";

    public Ticket create(Ticket ticket) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addTicket,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());
            statement.setDate(5, Date.valueOf(ticket.getDeadline()));
            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    ticket.setId(resultSet.getInt(1));
                }
            }

            return ticket;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    public Ticket findById(int id) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findTicketById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTicket(resultSet);
                }
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return null;
    }

    public List<Ticket> findAll() {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findAllTickets);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tickets.add(mapTicket(resultSet));
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> findByProjectId(int projectId) {

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

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> findByAssignedUser(int userId) {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findTicketsByAssignedUser)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> search(String keyword) {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(searchTickets)) {

            String searchValue = "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return tickets;
    }

    public boolean update(Ticket ticket) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateTicket)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());
            statement.setDate(5, Date.valueOf(ticket.getDeadline()));
            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());
            statement.setInt(8, ticket.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    public boolean assignTicket(int ticketId, int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(assignTicket)) {

            statement.setInt(1, userId);
            statement.setInt(2, ticketId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int ticketId, String status) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateStatus)) {

            statement.setString(1, status);
            statement.setInt(2, ticketId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteTicket)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }

    private Ticket mapTicket(ResultSet resultSet) throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setId(resultSet.getInt("id"));
        ticket.setProjectId(resultSet.getInt("project_id"));
        ticket.setTitle(resultSet.getString("title"));
        ticket.setDescription(resultSet.getString("description"));
        ticket.setPriority(resultSet.getString("priority"));

        Date deadline = resultSet.getDate("deadline");

        if (deadline != null) {
            ticket.setDeadline(deadline.toLocalDate());
        }

        ticket.setAssignedTo(resultSet.getInt("assigned_to"));
        ticket.setStatus(resultSet.getString("status"));

        Timestamp createdAt =
                resultSet.getTimestamp("created_at");

        ticket.setCreatedAt(createdAt);

        return ticket;
    }
}