package com.ptms.app.service;

import com.ptms.app.dao.ProjectMemberDAO;
import com.ptms.app.dao.TicketManagementDAO;
import com.ptms.app.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketManagementService {

    private final TicketManagementDAO ticketDAO =
            new TicketManagementDAO();

    private final ProjectMemberDAO projectMemberDAO =
            new ProjectMemberDAO();

    public Ticket createTicket(Ticket ticket) {

        validateTicket(ticket);

        if (!isProjectMember(
                ticket.getProjectId(),
                ticket.getAssignedTo())) {

            throw new IllegalArgumentException(
                    "Assigned user is not a member of this project");
        }

        return ticketDAO.create(ticket);
    }

    public Ticket findTicketById(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ticket id");
        }

        return ticketDAO.findById(id);
    }

    public List<Ticket> findAllTickets() {
        return ticketDAO.findAll();
    }

    public List<Ticket> findTicketsByProject(int projectId) {

        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project id");
        }

        return ticketDAO.findByProjectId(projectId);
    }

    public List<Ticket> findTicketsByAssignedUser(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }

        return ticketDAO.findByAssignedUser(userId);
    }

    public List<Ticket> searchTickets(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Search keyword cannot be empty");
        }

        return ticketDAO.search(keyword);
    }

    public boolean updateTicket(Ticket ticket) {

        validateTicket(ticket);

        if (!isProjectMember(
                ticket.getProjectId(),
                ticket.getAssignedTo())) {

            throw new IllegalArgumentException(
                    "Assigned user is not a member of this project");
        }

        return ticketDAO.update(ticket);
    }

    public boolean assignTicket(int ticketId, int userId) {

        if (ticketId <= 0) {
            throw new IllegalArgumentException("Invalid ticket id");
        }

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }

        Ticket ticket = ticketDAO.findById(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found");
        }

        if (!isProjectMember(
                ticket.getProjectId(),
                userId)) {

            throw new IllegalArgumentException(
                    "User is not a member of this project");
        }

        return ticketDAO.assignTicket(ticketId, userId);
    }

    public boolean deleteTicket(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ticket id");
        }

        return ticketDAO.delete(id);
    }

    private boolean isProjectMember(int projectId, int userId) {

        try {

            return projectMemberDAO.isMember(
                    projectId,
                    userId);

        } catch (SQLException e) {

            throw new IllegalArgumentException(
                    "Unable to check project member");
        }
    }

    private void validateTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket cannot be null");
        }

        if (ticket.getProjectId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid project id");
        }

        if (ticket.getTitle() == null ||
                ticket.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ticket title cannot be empty");
        }

        if (ticket.getPriority() == null ||
                ticket.getPriority().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ticket priority cannot be empty");
        }

        if (ticket.getDeadline() == null) {
            throw new IllegalArgumentException(
                    "Ticket deadline cannot be empty");
        }

        if (ticket.getAssignedTo() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid assigned user id");
        }

        if (!isValidStatus(ticket.getStatus())) {
            throw new IllegalArgumentException(
                    "Invalid ticket status");
        }
    }

    private boolean isValidStatus(String status) {

        return "OPEN".equals(status)
                || "IN_PROGRESS".equals(status)
                || "IMPLEMENTED".equals(status)
                || "COMPLETED".equals(status);
    }
}