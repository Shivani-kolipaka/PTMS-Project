package com.ptms.app.controller;

import com.ptms.app.model.User;
import com.ptms.app.service.UserService;

import java.util.Scanner;

public class LoginController {

    private final UserService userService =
            new UserService();

    private final Scanner scanner =
            new Scanner(System.in);

    public User login() {

        System.out.println("\n===== PTMS Login =====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {

            User user =
                    userService.login(username, password);

            if (user != null) {

                System.out.println(
                        "Login successful.");

                return user;
            }

            System.out.println(
                    "Invalid username or password.");

        } catch (Exception e) {

            System.out.println(
                    "Login error: " + e.getMessage());
        }

        return null;
    }
}