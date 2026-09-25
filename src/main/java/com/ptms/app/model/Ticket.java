package com.ptms.app.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Ticket {

    private int id;
    private int projectId;
    private String title;
    private String description;
    private String priority;
    private LocalDate deadline;
    private int assignedTo;
    private String status;
    private Timestamp createdAt;

    public Ticket() {
    }

    public Ticket(int id, int projectId, String title,
                  String description, String priority,
                  LocalDate deadline, int assignedTo,
                  String status, Timestamp createdAt) {

        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.assignedTo = assignedTo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", assignedTo=" + assignedTo +
                ", status='" + status + '\'' +
                '}';
    }
}
