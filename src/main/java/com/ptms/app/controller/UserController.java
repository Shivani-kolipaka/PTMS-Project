package com.ptms.app.controller;

import com.ptms.app.model.User;
import com.ptms.app.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserController {

    private final UserService userService =
            new UserService();

    private final Scanner scanner =
            new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== User Management =====");
            System.out.println("1. Add user");
            System.out.println("2. View all users");
            System.out.println("3. View user by id");
            System.out.println("4. Search users");
            System.out.println("5. Update user");
            System.out.println("6. Delete user");
            System.out.println("7. Deactivate user");
            System.out.println("0. Back");

            System.out.print("Enter your choice: ");

            try {

                int choice =
                        Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addUser();
                        break;

                    case 2:
                        viewAllUsers();
                        break;

                    case 3:
                        viewUserById();
                        break;

                    case 4:
                        searchUsers();
                        break;

                    case 5:
                        updateUser();
                        break;

                    case 6:
                        deleteUser();
                        break;

                    case 7:
                        deactivateUser();
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println(
                        "Error: " + e.getMessage());
            }
        }
    }

    private void addUser() {

        try {

            User user = new User();

            System.out.print("First name: ");
            user.setFirstName(scanner.nextLine());

            System.out.print("Last name: ");
            user.setLastName(scanner.nextLine());

            System.out.print("Username: ");
            user.setUsername(scanner.nextLine());

            System.out.print("Email: ");
            user.setEmail(scanner.nextLine());

            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());

            System.out.print("Role: ");
            user.setRoleName(scanner.nextLine());

            System.out.print("Date of birth (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            if (!date.trim().isEmpty()) {
                user.setDateOfBirth(
                        LocalDate.parse(date));
            }

            System.out.print("Mobile number: ");
            user.setMobileNumber(scanner.nextLine());

            System.out.print("Gender: ");
            user.setGender(scanner.nextLine());

            user.setActive(true);

            userService.createUser(user);

            System.out.println(
                    "User added successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void viewAllUsers() {

        try {

            List<User> users =
                    userService.findAllUsers();

            if (users.isEmpty()) {
                System.out.println(
                        "No users found.");
                return;
            }

            for (User user : users) {
                System.out.println(user);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void viewUserById() {

        try {

            System.out.print("Enter user id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            User user =
                    userService.findUserById(id);

            if (user == null) {
                System.out.println(
                        "User not found.");
                return;
            }

            System.out.println(user);

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void searchUsers() {

        try {

            System.out.print(
                    "Enter search keyword: ");

            String keyword =
                    scanner.nextLine();

            List<User> users =
                    userService.searchUsers(keyword);

            if (users.isEmpty()) {
                System.out.println(
                        "No users found.");
                return;
            }

            for (User user : users) {
                System.out.println(user);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void updateUser() {

        try {

            System.out.print("Enter user id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            User user =
                    userService.findUserById(id);

            if (user == null) {
                System.out.println(
                        "User not found.");
                return;
            }

            System.out.print("First name: ");
            user.setFirstName(scanner.nextLine());

            System.out.print("Last name: ");
            user.setLastName(scanner.nextLine());

            System.out.print("Username: ");
            user.setUsername(scanner.nextLine());

            System.out.print("Email: ");
            user.setEmail(scanner.nextLine());

            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());

            System.out.print("Role: ");
            user.setRoleName(scanner.nextLine());

            System.out.print(
                    "Date of birth (YYYY-MM-DD): ");

            String date =
                    scanner.nextLine();

            if (!date.trim().isEmpty()) {
                user.setDateOfBirth(
                        LocalDate.parse(date));
            }

            System.out.print("Mobile number: ");
            user.setMobileNumber(scanner.nextLine());

            System.out.print("Gender: ");
            user.setGender(scanner.nextLine());

            userService.updateUser(user);

            System.out.println(
                    "User updated successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void deleteUser() {

        try {

            System.out.print("Enter user id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            boolean result =
                    userService.deleteUser(id);

            if (result) {
                System.out.println(
                        "User deleted successfully.");
            } else {
                System.out.println(
                        "User not found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    private void deactivateUser() {

        try {

            System.out.print("Enter user id: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            boolean result =
                    userService.deactivateUser(id);

            if (result) {
                System.out.println(
                        "User deactivated successfully.");
            } else {
                System.out.println(
                        "User not found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }
}