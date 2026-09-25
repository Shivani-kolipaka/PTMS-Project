package com.ptms.app.model;

import java.sql.Timestamp;

public class ProjectMember {

    private int projectId;
    private int userId;
    private String projectRole;
    private Timestamp joinedAt;

    public ProjectMember() {
    }

    public ProjectMember(int projectId, int userId,
                         String projectRole, Timestamp joinedAt) {
        this.projectId = projectId;
        this.userId = userId;
        this.projectRole = projectRole;
        this.joinedAt = joinedAt;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
}