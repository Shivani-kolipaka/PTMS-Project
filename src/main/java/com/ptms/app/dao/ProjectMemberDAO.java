package com.ptms.app.dao;

import com.ptms.app.model.ProjectMember;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProjectMemberDAO {

    private final Logger logger =
            Logger.getLogger(ProjectMemberDAO.class.getName());

    private final String addProjectMember =
            "INSERT INTO project_members (project_id, user_id, project_role) VALUES (?, ?, ?)";

    private final String findProjectMembers =
            "SELECT project_id, user_id, project_role, joined_at " +
                    "FROM project_members WHERE project_id = ?";

    private final String deleteProjectMember =
            "DELETE FROM project_members WHERE project_id = ? AND user_id = ?";


    // CREATE
    public ProjectMember create(ProjectMember member) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(addProjectMember)) {

            statement.setInt(1, member.getProjectId());
            statement.setInt(2, member.getUserId());
            statement.setString(3, member.getProjectRole());

            statement.executeUpdate();

            logger.info("Project member added");
        }

        return member;
    }


    // READ - project members
    public List<ProjectMember> findByProjectId(int projectId)
            throws SQLException {

        List<ProjectMember> members = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(findProjectMembers)) {

            statement.setInt(1, projectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    ProjectMember member = new ProjectMember();

                    member.setProjectId(
                            resultSet.getInt("project_id"));

                    member.setUserId(
                            resultSet.getInt("user_id"));

                    member.setProjectRole(
                            resultSet.getString("project_role"));

                    member.setJoinedAt(
                            resultSet.getTimestamp("joined_at"));

                    members.add(member);
                }
            }
        }

        return members;
    }


    // DELETE
    public boolean delete(int projectId, int userId)
            throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(deleteProjectMember)) {

            statement.setInt(1, projectId);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();

            if (result > 0) {
                logger.info("Project member deleted");
            }

            return result > 0;
        }
    }
}