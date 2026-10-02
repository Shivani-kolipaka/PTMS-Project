package com.ptms.app.controller;

import com.ptms.app.model.Client;
import com.ptms.app.service.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientController {

    private final ClientService clientService =
            new ClientService();

    private final Scanner scanner =
            new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== Client Management =====");
            System.out.println("1. Add client");
            System.out.println("2. View all clients");
            System.out.println("3. View client by id");
            System.out.println("4. Search clients");
            System.out.println("5. Update client");
            System.out.println("6. Delete client");
            System.out.println("0. Back");

            System.out.print("Enter your choice: ");

            try {

                int choice =
                        Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addClient();
                        break;

                    case 2:
                        viewAllClients();
                        break;

                    case 3:
                        viewClientById();
                        break;

                    case 4:
                        searchClients();
                        break;

                    case 5:
                        updateClient();
                        break;

                    case 6:
                        deleteClient();
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

    private void addClient() {

        try {

            Client client = new Client();

            System.out.print("Name: ");
            client.setName(scanner.nextLine());

            System.out.print("Email: ");
            client.setEmail(scanner.nextLine());

            System.out.print("Phone: ");
            client.setPhone(scanner.nextLine());

            System.out.print("Company name: ");
            client.setCompanyName(scanner.nextLine());

            clientService.createClient(client);

            System.out.println("Client added successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllClients() {

        try {

            List<Client> clients =
                    clientService.findAllClients();

            if (clients.isEmpty()) {
                System.out.println("No clients found.");
                return;
            }

            for (Client client : clients) {
                System.out.println(client);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewClientById() {

        try {

            System.out.print("Enter client id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            Client client =
                    clientService.findClientById(id);

            if (client == null) {
                System.out.println("Client not found.");
                return;
            }

            System.out.println(client);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchClients() {

        try {

            System.out.print("Enter search keyword: ");

            String keyword =
                    scanner.nextLine();

            List<Client> clients =
                    clientService.searchClients(keyword);

            if (clients.isEmpty()) {
                System.out.println("No clients found.");
                return;
            }

            for (Client client : clients) {
                System.out.println(client);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateClient() {

        try {

            System.out.print("Enter client id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            Client client =
                    clientService.findClientById(id);

            if (client == null) {
                System.out.println("Client not found.");
                return;
            }

            System.out.print("Name: ");
            client.setName(scanner.nextLine());

            System.out.print("Email: ");
            client.setEmail(scanner.nextLine());

            System.out.print("Phone: ");
            client.setPhone(scanner.nextLine());

            System.out.print("Company name: ");
            client.setCompanyName(scanner.nextLine());

            clientService.updateClient(client);

            System.out.println("Client updated successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteClient() {

        try {

            System.out.print("Enter client id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            clientService.deleteClient(id);

            System.out.println("Client deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
