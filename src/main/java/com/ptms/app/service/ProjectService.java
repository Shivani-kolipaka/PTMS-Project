package com.ptms.app.service;

import com.ptms.app.dao.ProjectDAO;
import com.ptms.app.model.Project;

import java.sql.SQLException;
import java.util.List;

public class ProjectService {

    private final ProjectDAO projectDAO = new ProjectDAO();

    public Project createProject(Project project) throws SQLException {

        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name is required");
        }

        if (project.getManagerId() <= 0) {
            throw new IllegalArgumentException("Valid manager ID is required");
        }

        if (project.getClientId() <= 0) {
            throw new IllegalArgumentException("Valid client ID is required");
        }

        if (project.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (project.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline is required");
        }

        if (project.getDeadline().isBefore(project.getStartDate())) {
            throw new IllegalArgumentException(
                    "Deadline cannot be before start date");
        }

        return projectDAO.create(project);
    }

    public Project findProjectById(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        return projectDAO.findById(id);
    }

    public List<Project> findAllProjects() throws SQLException {

        return projectDAO.findAll();
    }

    public boolean updateProject(Project project) throws SQLException {

        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (project.getId() <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name is required");
        }

        if (project.getManagerId() <= 0) {
            throw new IllegalArgumentException("Valid manager ID is required");
        }

        if (project.getClientId() <= 0) {
            throw new IllegalArgumentException("Valid client ID is required");
        }

        if (project.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (project.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline is required");
        }

        if (project.getDeadline().isBefore(project.getStartDate())) {
            throw new IllegalArgumentException(
                    "Deadline cannot be before start date");
        }

        return projectDAO.update(project);
    }

    public boolean deleteProject(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        return projectDAO.delete(id);
    }

    public List<Project> searchProjects(String keyword) throws SQLException {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword is required");
        }

        return projectDAO.searchProjects(keyword);
    }
}