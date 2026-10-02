package com.ptms.app.dao;

import com.ptms.app.model.Project;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProjectDAO {

    private final Logger logger =
            Logger.getLogger(ProjectDAO.class.getName());

    private final String addProject =
            "INSERT INTO projects " +
                    "(name, requirements, manager_id, team_lead_id, client_id, " +
                    "domain, cost, start_date, deadline, priority, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String findAllProjects =
            "SELECT id, name, requirements, manager_id, team_lead_id, " +
                    "client_id, domain, cost, start_date, deadline, priority, status " +
                    "FROM projects";

    private final String findProjectById =
            "SELECT id, name, requirements, manager_id, team_lead_id, " +
                    "client_id, domain, cost, start_date, deadline, priority, status " +
                    "FROM projects WHERE id = ?";

    private final String searchProjects =
            "SELECT id, name, requirements, manager_id, team_lead_id, " +
                    "client_id, domain, cost, start_date, deadline, priority, status " +
                    "FROM projects " +
                    "WHERE name LIKE ? OR domain LIKE ? OR status LIKE ?";

    private final String updateProject =
            "UPDATE projects SET name = ?, requirements = ?, manager_id = ?, " +
                    "team_lead_id = ?, client_id = ?, domain = ?, cost = ?, " +
                    "start_date = ?, deadline = ?, priority = ?, status = ? " +
                    "WHERE id = ?";

    private final String deleteProject =
            "DELETE FROM projects WHERE id = ?";

    private final String findProjectsByMember =
            "SELECT p.id, p.name, p.requirements, p.manager_id, " +
                    "p.team_lead_id, p.client_id, p.domain, p.cost, p.start_date, " +
                    "p.deadline, p.priority, p.status " +
                    "FROM projects p " +
                    "JOIN project_members pm ON p.id = pm.project_id " +
                    "WHERE pm.user_id = ?";

    public Project create(Project project) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             addProject,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getRequirements());
            statement.setInt(3, project.getManagerId());
            statement.setInt(4, project.getTeamLeadId());
            statement.setInt(5, project.getClientId());
            statement.setString(6, project.getDomain());
            statement.setDouble(7, project.getCost());
            statement.setDate(8,
                    java.sql.Date.valueOf(project.getStartDate()));
            statement.setDate(9,
                    java.sql.Date.valueOf(project.getDeadline()));
            statement.setString(10, project.getPriority());
            statement.setString(11, project.getStatus());

            statement.executeUpdate();

            try (ResultSet resultSet =
                         statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    project.setId(resultSet.getInt(1));
                }
            }

            logger.info("Project added successfully.");
        }

        return project;
    }

    public List<Project> findAll() throws SQLException {

        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findAllProjects);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                projects.add(mapProject(resultSet));
            }
        }

        return projects;
    }

    public Project findById(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findProjectById)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapProject(resultSet);
                }
            }
        }

        return null;
    }

    public List<Project> search(String keyword) throws SQLException {

        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(searchProjects)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    projects.add(mapProject(resultSet));
                }
            }
        }

        return projects;
    }

    public void update(Project project) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateProject)) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getRequirements());
            statement.setInt(3, project.getManagerId());
            statement.setInt(4, project.getTeamLeadId());
            statement.setInt(5, project.getClientId());
            statement.setString(6, project.getDomain());
            statement.setDouble(7, project.getCost());
            statement.setDate(8,
                    java.sql.Date.valueOf(project.getStartDate()));
            statement.setDate(9,
                    java.sql.Date.valueOf(project.getDeadline()));
            statement.setString(10, project.getPriority());
            statement.setString(11, project.getStatus());
            statement.setInt(12, project.getId());

            statement.executeUpdate();

            logger.info("Project updated successfully.");
        }
    }

    public void delete(int id) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteProject)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            logger.info("Project deleted successfully.");
        }
    }

    public List<Project> findProjectsByMember(int userId)
            throws SQLException {

        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findProjectsByMember)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    projects.add(mapProject(resultSet));
                }
            }
        }

        return projects;
    }

    private Project mapProject(ResultSet resultSet)
            throws SQLException {

        Project project = new Project();

        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setRequirements(
                resultSet.getString("requirements"));
        project.setManagerId(
                resultSet.getInt("manager_id"));
        project.setTeamLeadId(
                resultSet.getInt("team_lead_id"));
        project.setClientId(
                resultSet.getInt("client_id"));
        project.setDomain(
                resultSet.getString("domain"));
        project.setCost(
                resultSet.getDouble("cost"));

        if (resultSet.getDate("start_date") != null) {
            project.setStartDate(
                    resultSet.getDate("start_date").toLocalDate());
        }

        if (resultSet.getDate("deadline") != null) {
            project.setDeadline(
                    resultSet.getDate("deadline").toLocalDate());
        }

        project.setPriority(
                resultSet.getString("priority"));
        project.setStatus(
                resultSet.getString("status"));

        return project;
    }
}