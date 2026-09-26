package com.ptms.app.service;

import com.ptms.app.dao.TicketTrackingDAO;
import com.ptms.app.model.TicketTracking;

import java.sql.SQLException;

public class TicketTrackingService {

    private final TicketTrackingDAO ticketTrackingDAO =
            new TicketTrackingDAO();

    public TicketTracking createTracking(TicketTracking tracking)
            throws SQLException {

        validateTracking(tracking);

        return ticketTrackingDAO.create(tracking);
    }

    public TicketTracking findTrackingByTicketId(int ticketId)
            throws SQLException {

        if (ticketId <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }

        return ticketTrackingDAO.findByTicketId(ticketId);
    }

    public TicketTracking findTrackingById(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid ticket tracking ID");
        }

        return ticketTrackingDAO.findById(id);
    }

    public boolean updateTracking(TicketTracking tracking)
            throws SQLException {

        validateTracking(tracking);

        if (tracking.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid ticket tracking ID");
        }

        return ticketTrackingDAO.update(tracking);
    }

    private void validateTracking(TicketTracking tracking) {

        if (tracking == null) {
            throw new IllegalArgumentException(
                    "Ticket tracking cannot be null");
        }

        if (tracking.getTicketId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid ticket ID is required");
        }

        if (tracking.getStatus() == null ||
                tracking.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ticket status is required");
        }

        if (tracking.getProgress() < 0 ||
                tracking.getProgress() > 100) {
            throw new IllegalArgumentException(
                    "Progress must be between 0 and 100");
        }

        if (tracking.getUpdatedBy() <= 0) {
            throw new IllegalArgumentException(
                    "Valid updated user ID is required");
        }
    }
}