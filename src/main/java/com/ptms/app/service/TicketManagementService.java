package com.ptms.app.service;

import com.ptms.app.dao.TicketManagementDAO;
import com.ptms.app.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketManagementService {

    private final TicketManagementDAO ticketDAO =
            new TicketManagementDAO();

    public Ticket createTicket(Ticket ticket) throws SQLException {

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }

        if (ticket.getProjectId() <= 0) {
            throw new IllegalArgumentException("Valid project ID is required");
        }

        if (ticket.getTitle() == null ||
                ticket.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket title is required");
        }

        if (ticket.getPriority() == null ||
                ticket.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket priority is required");
        }

        if (ticket.getDeadline() == null) {
            throw new IllegalArgumentException("Ticket deadline is required");
        }

        if (ticket.getAssignedTo() <= 0) {
            throw new IllegalArgumentException(
                    "Valid assigned user ID is required");
        }

        if (ticket.getStatus() == null ||
                ticket.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket status is required");
        }

        return ticketDAO.create(ticket);
    }

    public Ticket findTicketById(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }

        return ticketDAO.findById(id);
    }

    public List<Ticket> findAllTickets() throws SQLException {

        return ticketDAO.findAll();
    }

    public List<Ticket> findTicketsByProject(int projectId)
            throws SQLException {

        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        return ticketDAO.findByProjectId(projectId);
    }

    public boolean updateTicket(Ticket ticket) throws SQLException {

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }

        if (ticket.getId() <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }

        if (ticket.getProjectId() <= 0) {
            throw new IllegalArgumentException("Valid project ID is required");
        }

        if (ticket.getTitle() == null ||
                ticket.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket title is required");
        }

        if (ticket.getPriority() == null ||
                ticket.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket priority is required");
        }

        if (ticket.getDeadline() == null) {
            throw new IllegalArgumentException("Ticket deadline is required");
        }

        if (ticket.getAssignedTo() <= 0) {
            throw new IllegalArgumentException(
                    "Valid assigned user ID is required");
        }

        if (ticket.getStatus() == null ||
                ticket.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket status is required");
        }

        return ticketDAO.update(ticket);
    }

    public boolean deleteTicket(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }

        return ticketDAO.delete(id);
    }

    public List<Ticket> searchTickets(String keyword) throws SQLException {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword is required");
        }

        return ticketDAO.searchTickets(keyword);
    }
}
