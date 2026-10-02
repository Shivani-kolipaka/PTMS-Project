package com.ptms.app.controller;

import com.ptms.app.model.Project;
import com.ptms.app.model.User;
import com.ptms.app.service.ProjectService;

import java.util.List;
import java.util.Scanner;

public class RoleController {

    private final Scanner scanner = new Scanner(System.in);

    private final UserController userController =
            new UserController();

    private final ClientController clientController =
            new ClientController();

    private final ProjectController projectController =
            new ProjectController();

    private final TicketManagementController ticketController =
            new TicketManagementController();

    private final TicketTrackingController trackingController =
            new TicketTrackingController();

    private final ProjectService projectService =
            new ProjectService();

    public void showMenu(User user) {

        String role = user.getRoleName();

        if (role.equals("ADMIN")) {
            showAdminMenu();
        } else if (role.equals("PROJECT_MANAGER")) {
            showProjectManagerMenu();
        } else if (role.equals("TEAM_LEAD")) {
            showTeamLeadMenu();
        } else if (role.equals("TEAM_MEMBER")) {
            showTeamMemberMenu(user);
        } else {
            System.out.println("Unknown role.");
        }
    }

    private void showAdminMenu() {

        while (true) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. User Management");
            System.out.println("2. Client Management");
            System.out.println("3. View Projects");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                userController.showMenu();
            } else if (choice == 2) {
                clientController.showMenu();
            } else if (choice == 3) {
                projectController.showMenu();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void showProjectManagerMenu() {

        while (true) {

            System.out.println("\n===== PROJECT MANAGER MENU =====");
            System.out.println("1. Project Management");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                projectController.showMenu();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void showTeamLeadMenu() {

        while (true) {

            System.out.println("\n===== TEAM LEAD MENU =====");
            System.out.println("1. Project Management");
            System.out.println("2. Ticket Management");
            System.out.println("3. Ticket Tracking");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                projectController.showMenu();
            } else if (choice == 2) {
                ticketController.showMenu();
            } else if (choice == 3) {
                trackingController.showMenu();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void showTeamMemberMenu(User user) {

        while (true) {

            System.out.println("\n===== TEAM MEMBER MENU =====");
            System.out.println("1. View Projects");
            System.out.println("2. View Tickets");
            System.out.println("3. Update Ticket");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {

                try {

                    List<Project> projects =
                            projectService.findProjectsByMember(user.getId());

                    if (projects.isEmpty()) {
                        System.out.println("No assigned projects found.");
                    } else {

                        System.out.println("\n===== ASSIGNED PROJECTS =====");

                        for (Project project : projects) {
                            System.out.println(project);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (choice == 2) {

                ticketController.showAssignedTickets(user.getId());

            } else if (choice == 3) {

                trackingController.updateTicket(user.getId());

            } else if (choice == 0) {

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }
}