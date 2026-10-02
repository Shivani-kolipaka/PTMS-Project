package com.ptms.app.controller;

import com.ptms.app.model.Ticket;
import com.ptms.app.service.TicketManagementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TicketManagementController {

    private final TicketManagementService ticketService =
            new TicketManagementService();

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== Ticket Management =====");
            System.out.println("1. Add ticket");
            System.out.println("2. View all tickets");
            System.out.println("3. View ticket by id");
            System.out.println("4. View tickets by project");
            System.out.println("5. Search tickets");
            System.out.println("6. Update ticket");
            System.out.println("7. Assign ticket");
            System.out.println("8. Delete ticket");
            System.out.println("0. Back to main menu");

            System.out.print("Enter your choice: ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addTicket();
                        break;

                    case 2:
                        viewAllTickets();
                        break;

                    case 3:
                        viewTicketById();
                        break;

                    case 4:
                        viewTicketsByProject();
                        break;

                    case 5:
                        searchTickets();
                        break;

                    case 6:
                        updateTicket();
                        break;

                    case 7:
                        assignTicket();
                        break;

                    case 8:
                        deleteTicket();
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void showAssignedTickets(int userId) {

        try {

            List<Ticket> tickets =
                    ticketService.findTicketsByAssignedUser(userId);

            if (tickets.isEmpty()) {
                System.out.println("No assigned tickets found.");
                return;
            }

            System.out.println("\n===== ASSIGNED TICKETS =====");

            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addTicket() {

        try {

            Ticket ticket = new Ticket();

            System.out.print("Project id: ");
            ticket.setProjectId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Title: ");
            ticket.setTitle(scanner.nextLine());

            System.out.print("Description: ");
            ticket.setDescription(scanner.nextLine());

            System.out.print("Priority: ");
            ticket.setPriority(scanner.nextLine());

            System.out.print("Deadline (yyyy-mm-dd): ");
            ticket.setDeadline(LocalDate.parse(scanner.nextLine()));

            System.out.print("Assigned user id: ");
            ticket.setAssignedTo(Integer.parseInt(scanner.nextLine()));

            System.out.print("Status: ");
            ticket.setStatus(scanner.nextLine());

            Ticket createdTicket = ticketService.createTicket(ticket);

            System.out.println("Ticket added successfully.");
            System.out.println(createdTicket);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllTickets() {

        try {

            List<Ticket> tickets = ticketService.findAllTickets();

            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
                return;
            }

            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewTicketById() {

        try {

            System.out.print("Enter ticket id: ");

            int id = Integer.parseInt(scanner.nextLine());

            Ticket ticket = ticketService.findTicketById(id);

            if (ticket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            System.out.println(ticket);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewTicketsByProject() {

        try {

            System.out.print("Enter project id: ");

            int projectId = Integer.parseInt(scanner.nextLine());

            List<Ticket> tickets =
                    ticketService.findTicketsByProject(projectId);

            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
                return;
            }

            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchTickets() {

        try {

            System.out.print("Enter search keyword: ");

            String keyword = scanner.nextLine();

            List<Ticket> tickets =
                    ticketService.searchTickets(keyword);

            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
                return;
            }

            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateTicket() {

        try {

            System.out.print("Enter ticket id: ");

            int id = Integer.parseInt(scanner.nextLine());

            Ticket ticket = ticketService.findTicketById(id);

            if (ticket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            System.out.print("Project id: ");
            ticket.setProjectId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Title: ");
            ticket.setTitle(scanner.nextLine());

            System.out.print("Description: ");
            ticket.setDescription(scanner.nextLine());

            System.out.print("Priority: ");
            ticket.setPriority(scanner.nextLine());

            System.out.print("Deadline (yyyy-mm-dd): ");
            ticket.setDeadline(LocalDate.parse(scanner.nextLine()));

            System.out.print("Assigned user id: ");
            ticket.setAssignedTo(Integer.parseInt(scanner.nextLine()));

            System.out.print("Status: ");
            ticket.setStatus(scanner.nextLine());

            boolean updated = ticketService.updateTicket(ticket);

            if (updated) {
                System.out.println("Ticket updated successfully.");
            } else {
                System.out.println("Ticket was not updated.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void assignTicket() {

        try {

            System.out.print("Enter ticket id: ");

            int ticketId = Integer.parseInt(scanner.nextLine());

            Ticket ticket = ticketService.findTicketById(ticketId);

            if (ticket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            System.out.println("Current assigned user id: "
                    + ticket.getAssignedTo());

            System.out.print("Enter new assigned user id: ");

            int userId = Integer.parseInt(scanner.nextLine());

            boolean updated =
                    ticketService.assignTicket(ticketId, userId);

            if (updated) {
                System.out.println("Ticket assigned successfully.");
            } else {
                System.out.println("Ticket was not assigned.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteTicket() {

        try {

            System.out.print("Enter ticket id: ");

            int id = Integer.parseInt(scanner.nextLine());

            boolean deleted = ticketService.deleteTicket(id);

            if (deleted) {
                System.out.println("Ticket deleted successfully.");
            } else {
                System.out.println("Ticket was not found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}