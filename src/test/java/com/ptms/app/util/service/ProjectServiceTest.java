package com.ptms.app.service;

import com.ptms.app.model.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {

    private final ProjectService projectService =
            new ProjectService();

    @Test
    void createProjectShouldRejectNullProject() {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(null)
        );
    }

    @Test
    void createProjectShouldRejectMissingName()
            throws SQLException {

        Project project = new Project();
        project.setName("");
        project.setManagerId(1);
        project.setClientId(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );
    }

    @Test
    void createProjectShouldRejectInvalidManagerId()
            throws SQLException {

        Project project = new Project();
        project.setName("Test Project");
        project.setManagerId(0);
        project.setClientId(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );
    }

    @Test
    void createProjectShouldRejectInvalidClientId()
            throws SQLException {

        Project project = new Project();
        project.setName("Test Project");
        project.setManagerId(1);
        project.setClientId(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );
    }

    @Test
    void createProjectShouldRejectMissingStartDate()
            throws SQLException {

        Project project = new Project();
        project.setName("Test Project");
        project.setManagerId(1);
        project.setClientId(1);
        project.setDeadline(LocalDate.now());

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );
    }

    @Test
    void createProjectShouldRejectMissingDeadline()
            throws SQLException {

        Project project = new Project();
        project.setName("Test Project");
        project.setManagerId(1);
        project.setClientId(1);
        project.setStartDate(LocalDate.now());

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );
    }

    @Test
    void findProjectShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.findProjectById(0)
        );
    }

    @Test
    void updateProjectShouldRejectNullProject()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.updateProject(null)
        );
    }

    @Test
    void updateProjectShouldRejectInvalidId()
            throws SQLException {

        Project project = new Project();
        project.setId(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.updateProject(project)
        );
    }

    @Test
    void deleteProjectShouldRejectInvalidId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.deleteProject(0)
        );
    }

    @Test
    void searchProjectsShouldRejectEmptyKeyword()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.searchProjects("")
        );
    }

    @Test
    void findProjectsByMemberShouldRejectInvalidUserId()
            throws SQLException {

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.findProjectsByMember(0)
        );
    }
}

