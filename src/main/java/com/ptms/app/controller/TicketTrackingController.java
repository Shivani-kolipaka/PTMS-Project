package com.ptms.app.controller;

import com.ptms.app.model.TicketTracking;
import com.ptms.app.service.TicketTrackingService;

import java.util.Scanner;

public class TicketTrackingController {

    private final TicketTrackingService trackingService =
            new TicketTrackingService();

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== Ticket Tracking =====");
            System.out.println("1. Add ticket update");
            System.out.println("2. View tracking by ticket");
            System.out.println("3. View tracking by id");
            System.out.println("0. Back to main menu");

            System.out.print("Enter your choice: ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addTracking();
                        break;

                    case 2:
                        viewTrackingByTicket();
                        break;

                    case 3:
                        viewTrackingById();
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

    public void updateTicket(int userId) {

        try {

            System.out.print("Enter ticket id: ");
            int ticketId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter status: ");
            String status = scanner.nextLine();

            System.out.print("Enter progress: ");
            int progress = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter comment: ");
            String comment = scanner.nextLine();

            TicketTracking tracking = new TicketTracking();

            tracking.setTicketId(ticketId);
            tracking.setStatus(status);
            tracking.setProgress(progress);
            tracking.setComment(comment);
            tracking.setUpdatedBy(userId);

            trackingService.createTracking(tracking);

            System.out.println("Ticket updated successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addTracking() {

        try {

            TicketTracking tracking = new TicketTracking();

            System.out.print("Ticket id: ");
            tracking.setTicketId(
                    Integer.parseInt(scanner.nextLine()));

            System.out.print("Status: ");
            tracking.setStatus(scanner.nextLine());

            System.out.print("Progress: ");
            tracking.setProgress(
                    Integer.parseInt(scanner.nextLine()));

            System.out.print("Comment: ");
            tracking.setComment(scanner.nextLine());

            System.out.print("Updated by user id: ");
            tracking.setUpdatedBy(
                    Integer.parseInt(scanner.nextLine()));

            trackingService.createTracking(tracking);

            System.out.println("Ticket update added successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewTrackingByTicket() {

        try {

            System.out.print("Enter ticket id: ");

            int ticketId =
                    Integer.parseInt(scanner.nextLine());

            TicketTracking tracking =
                    trackingService.findTrackingByTicketId(ticketId);

            if (tracking == null) {
                System.out.println("No ticket update found.");
                return;
            }

            System.out.println(tracking);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewTrackingById() {

        try {

            System.out.print("Enter tracking id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            TicketTracking tracking =
                    trackingService.findTrackingById(id);

            if (tracking == null) {
                System.out.println("Ticket update not found.");
                return;
            }

            System.out.println(tracking);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}