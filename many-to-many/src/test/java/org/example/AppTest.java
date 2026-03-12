package org.example;

import org.example.dao.ProjectDAO;
import org.example.dao.ProjectDAOImpl;
import org.example.entity.Employee;
import org.example.entity.Project;
import org.example.repository.JPAUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final ProjectDAO projectDAO = new ProjectDAOImpl();

    @BeforeEach
    void setUp() {
        projectDAO.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        JPAUtil.close();
    }

    @Test
    void shouldAssignAndRemoveEmployeesFromProjects() {
        Employee alice = projectDAO.createEmployee("Alice");
        Employee bob = projectDAO.createEmployee("Bob");
        Project alpha = projectDAO.createProject("Alpha", "Client A");
        Project beta = projectDAO.createProject("Beta", "Client B");

        projectDAO.assignEmployeeToProject(alice.getId(), alpha.getId());
        projectDAO.assignEmployeeToProject(alice.getId(), beta.getId());
        projectDAO.assignEmployeeToProject(bob.getId(), alpha.getId());

        List<Project> aliceProjects = projectDAO.getProjectsOfEmployee(alice.getId());
        List<Employee> alphaEmployees = projectDAO.getEmployeesOfProject(alpha.getId());
        List<Employee> multiProjectEmployees = projectDAO.getEmployeesWorkingOnMultipleProjects();
        List<String> workload = projectDAO.getProjectWorkload();

        assertEquals(2, aliceProjects.size());
        assertEquals(2, alphaEmployees.size());
        assertEquals(1, multiProjectEmployees.size());
        assertEquals("Alice", multiProjectEmployees.get(0).getName());
        assertTrue(workload.contains("Alpha:-2"));
        assertTrue(workload.contains("Beta:-1"));

        projectDAO.removeEmployeeFromProject(alice.getId(), alpha.getId());

        aliceProjects = projectDAO.getProjectsOfEmployee(alice.getId());
        alphaEmployees = projectDAO.getEmployeesOfProject(alpha.getId());

        assertEquals(1, aliceProjects.size());
        assertEquals("Beta", aliceProjects.get(0).getTitle());
        assertEquals(1, alphaEmployees.size());
        assertEquals("Bob", alphaEmployees.get(0).getName());
    }
}
