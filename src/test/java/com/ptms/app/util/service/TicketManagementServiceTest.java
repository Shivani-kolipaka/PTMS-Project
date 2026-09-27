package com.ptms.app.service;

import com.ptms.app.model.Ticket;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TicketManagementServiceTest {

    private final TicketManagementService ticketService =
            new TicketManagementService();

    @Test
    void createTicketShouldRejectNullTicket()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(null)
        );
    }

    @Test
    void createTicketShouldRejectInvalidProjectId()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(0);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("OPEN");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectMissingTitle()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("OPEN");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectMissingPriority()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("OPEN");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectMissingDeadline()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setAssignedTo(1);
        ticket.setStatus("OPEN");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectInvalidAssignedUser()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(0);
        ticket.setStatus("OPEN");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectMissingStatus()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void createTicketShouldRejectInvalidStatus()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("INVALID");

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.createTicket(ticket)
        );
    }

    @Test
    void findTicketShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.findTicketById(0)
        );
    }

    @Test
    void findTicketsByProjectShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.findTicketsByProject(0)
        );
    }

    @Test
    void updateTicketShouldRejectNullTicket()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.updateTicket(null)
        );
    }

    @Test
    void updateTicketShouldRejectInvalidId()
            throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setProjectId(1);
        ticket.setTitle("Test Ticket");
        ticket.setPriority("HIGH");
        ticket.setDeadline(LocalDate.now());
        ticket.setAssignedTo(1);
        ticket.setStatus("OPEN");
        ticket.setId(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.updateTicket(ticket)
        );
    }

    @Test
    void deleteTicketShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.deleteTicket(0)
        );
    }

    @Test
    void searchTicketsShouldRejectEmptyKeyword()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.searchTickets("")
        );
    }

    @Test
    void findTicketsByAssignedUserShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketService.findTicketsByAssignedUser(0)
        );
    }
}


