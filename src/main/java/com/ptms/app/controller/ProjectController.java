package com.ptms.app.controller;

import com.ptms.app.model.Project;
import com.ptms.app.service.ProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjectController {

    private final ProjectService projectService = new ProjectService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n===== Project Management =====");
            System.out.println("1. Add project");
            System.out.println("2. View all projects");
            System.out.println("3. View project by id");
            System.out.println("4. Search projects");
            System.out.println("5. Update project");
            System.out.println("6. Delete project");
            System.out.println("0. Back to main menu");

            System.out.print("Enter your choice: ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addProject();
                        break;

                    case 2:
                        viewAllProjects();
                        break;

                    case 3:
                        viewProjectById();
                        break;

                    case 4:
                        searchProjects();
                        break;

                    case 5:
                        updateProject();
                        break;

                    case 6:
                        deleteProject();
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

    private void addProject() {

        Project project = new Project();

        System.out.print("Project name: ");
        project.setName(scanner.nextLine());

        System.out.print("Requirements: ");
        project.setRequirements(scanner.nextLine());

        System.out.print("Manager id: ");
        project.setManagerId(Integer.parseInt(scanner.nextLine()));

        System.out.print("Team lead id: ");
        project.setTeamLeadId(Integer.parseInt(scanner.nextLine()));

        System.out.print("Client id: ");
        project.setClientId(Integer.parseInt(scanner.nextLine()));

        System.out.print("Domain: ");
        project.setDomain(scanner.nextLine());

        System.out.print("Cost: ");
        project.setCost(Double.parseDouble(scanner.nextLine()));

        System.out.print("Start date (yyyy-mm-dd): ");
        project.setStartDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("Deadline (yyyy-mm-dd): ");
        project.setDeadline(LocalDate.parse(scanner.nextLine()));

        System.out.print("Priority: ");
        project.setPriority(scanner.nextLine());

        System.out.print("Status: ");
        project.setStatus(scanner.nextLine());

        try {

            Project createdProject = projectService.createProject(project);

            System.out.println("Project added successfully.");
            System.out.println(createdProject);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllProjects() {

        try {

            List<Project> projects = projectService.findAllProjects();

            if (projects.isEmpty()) {
                System.out.println("No projects found.");
                return;
            }

            for (Project project : projects) {
                System.out.println(project);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewProjectById() {

        try {

            System.out.print("Enter project id: ");

            int id = Integer.parseInt(scanner.nextLine());

            Project project = projectService.findProjectById(id);

            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            System.out.println(project);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchProjects() {

        try {

            System.out.print("Enter search keyword: ");

            String keyword = scanner.nextLine();

            List<Project> projects = projectService.searchProjects(keyword);

            if (projects.isEmpty()) {
                System.out.println("No projects found.");
                return;
            }

            for (Project project : projects) {
                System.out.println(project);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateProject() {

        try {

            System.out.print("Enter project id: ");

            int id = Integer.parseInt(scanner.nextLine());

            Project project = projectService.findProjectById(id);

            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            System.out.print("Project name: ");
            project.setName(scanner.nextLine());

            System.out.print("Requirements: ");
            project.setRequirements(scanner.nextLine());

            System.out.print("Manager id: ");
            project.setManagerId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Team lead id: ");
            project.setTeamLeadId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Client id: ");
            project.setClientId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Domain: ");
            project.setDomain(scanner.nextLine());

            System.out.print("Cost: ");
            project.setCost(Double.parseDouble(scanner.nextLine()));

            System.out.print("Start date (yyyy-mm-dd): ");
            project.setStartDate(LocalDate.parse(scanner.nextLine()));

            System.out.print("Deadline (yyyy-mm-dd): ");
            project.setDeadline(LocalDate.parse(scanner.nextLine()));

            System.out.print("Priority: ");
            project.setPriority(scanner.nextLine());

            System.out.print("Status: ");
            project.setStatus(scanner.nextLine());

            boolean updated = projectService.updateProject(project);

            if (updated) {
                System.out.println("Project updated successfully.");
            } else {
                System.out.println("Project was not updated.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteProject() {

        try {

            System.out.print("Enter project id: ");

            int id = Integer.parseInt(scanner.nextLine());

            boolean deleted = projectService.deleteProject(id);

            if (deleted) {
                System.out.println("Project deleted successfully.");
            } else {
                System.out.println("Project was not found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
