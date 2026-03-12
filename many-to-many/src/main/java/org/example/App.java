package org.example;

import org.example.dao.ProjectDAO;
import org.example.dao.ProjectDAOImpl;
import org.example.entity.Employee;
import org.example.entity.Project;
import org.example.repository.JPAUtil;

public class App {
    public static void main(String[] args) {
        ProjectDAO projectDAO = new ProjectDAOImpl();

        Employee employee = projectDAO.createEmployee("Alice");
        Project project = projectDAO.createProject("Billing Platform", "Acme Corp");

        projectDAO.assignEmployeeToProject(employee.getId(), project.getId());

        System.out.println(projectDAO.getProjectsOfEmployee(employee.getId()).size());

        JPAUtil.close();
    }
}
