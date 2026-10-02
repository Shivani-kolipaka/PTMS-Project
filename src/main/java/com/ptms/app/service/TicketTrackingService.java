package com.ptms.app.service;

import com.ptms.app.dao.TicketManagementDAO;
import com.ptms.app.dao.TicketTrackingDAO;
import com.ptms.app.model.TicketTracking;

import java.sql.SQLException;

public class TicketTrackingService {

    private final TicketTrackingDAO ticketTrackingDAO =
            new TicketTrackingDAO();

    private final TicketManagementDAO ticketManagementDAO =
            new TicketManagementDAO();

    public TicketTracking createTracking(TicketTracking tracking)
            throws SQLException {

        validateTracking(tracking);

        TicketTracking existing =
                ticketTrackingDAO.findByTicketId(
                        tracking.getTicketId());

        if (existing != null) {

            tracking.setId(existing.getId());

            updateTracking(tracking);

            return tracking;
        }

        TicketTracking result =
                ticketTrackingDAO.create(tracking);

        ticketManagementDAO.updateStatus(
                tracking.getTicketId(),
                tracking.getStatus());

        return result;
    }

    public TicketTracking findTrackingByTicketId(int ticketId)
            throws SQLException {

        if (ticketId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid ticket ID");
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

        boolean result =
                ticketTrackingDAO.update(tracking);

        if (result) {
            ticketManagementDAO.updateStatus(
                    tracking.getTicketId(),
                    tracking.getStatus());
        }

        return result;
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

        String status =
                tracking.getStatus().toUpperCase();

        if (!status.equals("OPEN")
                && !status.equals("IN_PROGRESS")
                && !status.equals("IMPLEMENTED")
                && !status.equals("COMPLETED")) {

            throw new IllegalArgumentException(
                    "Invalid ticket status");
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

        if (status.equals("COMPLETED")
                && tracking.getProgress() != 100) {

            throw new IllegalArgumentException(
                    "Completed ticket must have 100% progress");
        }
    }
}