package com.ptms.app.controller;

import com.ptms.app.model.ProjectMember;
import com.ptms.app.service.ProjectMemberService;

import java.util.List;
import java.util.Scanner;

public class ProjectMemberController {

    private final ProjectMemberService projectMemberService =
            new ProjectMemberService();

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== Project Member Management =====");
            System.out.println("1. Add project member");
            System.out.println("2. View project members");
            System.out.println("3. Remove project member");
            System.out.println("0. Back to main menu");

            System.out.print("Enter your choice: ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addProjectMember();
                        break;

                    case 2:
                        viewProjectMembers();
                        break;

                    case 3:
                        removeProjectMember();
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

    private void addProjectMember() {

        try {

            ProjectMember member = new ProjectMember();

            System.out.print("Project id: ");
            member.setProjectId(Integer.parseInt(scanner.nextLine()));

            System.out.print("User id: ");
            member.setUserId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Project role: ");
            member.setProjectRole(scanner.nextLine());

            ProjectMember createdMember =
                    projectMemberService.addProjectMember(member);

            System.out.println("Project member added successfully.");
            System.out.println(createdMember);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewProjectMembers() {

        try {

            System.out.print("Enter project id: ");

            int projectId = Integer.parseInt(scanner.nextLine());

            List<ProjectMember> members =
                    projectMemberService.findProjectMembers(projectId);

            if (members.isEmpty()) {
                System.out.println("No project members found.");
                return;
            }

            for (ProjectMember member : members) {
                System.out.println(member);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removeProjectMember() {

        try {

            System.out.print("Project id: ");
            int projectId = Integer.parseInt(scanner.nextLine());

            System.out.print("User id: ");
            int userId = Integer.parseInt(scanner.nextLine());

            boolean removed =
                    projectMemberService.removeProjectMember(
                            projectId,
                            userId
                    );

            if (removed) {
                System.out.println("Project member removed successfully.");
            } else {
                System.out.println("Project member was not found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
