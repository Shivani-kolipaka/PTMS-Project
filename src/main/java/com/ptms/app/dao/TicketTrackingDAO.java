package com.ptms.app.dao;

import com.ptms.app.model.TicketTracking;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TicketTrackingDAO {

    private final Logger logger =
            Logger.getLogger(TicketTrackingDAO.class.getName());

    private final String addTicketTracking =
            "INSERT INTO ticket_tracking " +
                    "(ticket_id, status, progress, comment, updated_by) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final String findTrackingByTicketId =
            "SELECT id, ticket_id, status, progress, comment, " +
                    "updated_by, updated_at " +
                    "FROM ticket_tracking WHERE ticket_id = ?";

    private final String findTicketTrackingById =
            "SELECT id, ticket_id, status, progress, comment, " +
                    "updated_by, updated_at " +
                    "FROM ticket_tracking WHERE id = ?";

    private final String updateTicketTracking =
            "UPDATE ticket_tracking SET " +
                    "status = ?, progress = ?, comment = ?, updated_by = ? " +
                    "WHERE ticket_id = ?";


    // CREATE
    public TicketTracking create(TicketTracking tracking)
            throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addTicketTracking,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, tracking.getTicketId());
            statement.setString(2, tracking.getStatus());
            statement.setInt(3, tracking.getProgress());
            statement.setString(4, tracking.getComment());
            statement.setInt(5, tracking.getUpdatedBy());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    tracking.setId(resultSet.getInt(1));
                }
            }

            logger.info("Ticket tracking added");
        }

        return tracking;
    }


    // READ - by ticket ID
    public TicketTracking findByTicketId(int ticketId)
            throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             findTrackingByTicketId)) {

            statement.setInt(1, ticketId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTracking(resultSet);
                }
            }
        }

        return null;
    }


    // READ - by tracking ID
    public TicketTracking findById(int id)
            throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             findTicketTrackingById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTracking(resultSet);
                }
            }
        }

        return null;
    }


    // UPDATE
    public boolean update(TicketTracking tracking)
            throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             updateTicketTracking)) {

            statement.setString(1, tracking.getStatus());
            statement.setInt(2, tracking.getProgress());
            statement.setString(3, tracking.getComment());
            statement.setInt(4, tracking.getUpdatedBy());
            statement.setInt(5, tracking.getTicketId());

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Ticket tracking updated");
            }

            return result > 0;
        }
    }


    private TicketTracking mapTracking(ResultSet resultSet)
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setId(resultSet.getInt("id"));
        tracking.setTicketId(resultSet.getInt("ticket_id"));
        tracking.setStatus(resultSet.getString("status"));
        tracking.setProgress(resultSet.getInt("progress"));
        tracking.setComment(resultSet.getString("comment"));
        tracking.setUpdatedBy(resultSet.getInt("updated_by"));
        tracking.setUpdatedAt(
                resultSet.getTimestamp("updated_at"));

        return tracking;
    }
}
