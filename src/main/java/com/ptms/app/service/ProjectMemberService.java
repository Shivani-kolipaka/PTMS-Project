package com.ptms.app.service;

import com.ptms.app.dao.ProjectMemberDAO;
import com.ptms.app.model.ProjectMember;

import java.sql.SQLException;
import java.util.List;

public class ProjectMemberService {

    private final ProjectMemberDAO projectMemberDAO =
            new ProjectMemberDAO();

    public ProjectMember addProjectMember(ProjectMember member)
            throws SQLException {

        if (member == null) {
            throw new IllegalArgumentException("Project member cannot be null");
        }

        if (member.getProjectId() <= 0) {
            throw new IllegalArgumentException("Valid project ID is required");
        }

        if (member.getUserId() <= 0) {
            throw new IllegalArgumentException("Valid user ID is required");
        }

        if (member.getProjectRole() == null ||
                member.getProjectRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Project role is required");
        }

        return projectMemberDAO.create(member);
    }

    public List<ProjectMember> findProjectMembers(int projectId)
            throws SQLException {

        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        return projectMemberDAO.findByProjectId(projectId);
    }

    public boolean removeProjectMember(int projectId, int userId)
            throws SQLException {

        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project ID");
        }

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return projectMemberDAO.delete(projectId, userId);
    }
}
