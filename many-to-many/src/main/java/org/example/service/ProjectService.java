package org.example.service;

import org.example.dao.ProjectDAO;
import org.example.dao.ProjectDAOImpl;

public class ProjectService {

    public static void main(String[] args) {

        ProjectDAO dao = new ProjectDAOImpl();

        // 1. Create Employees
        dao.createEmployee("Rahul");
        dao.createEmployee("Aman");
        dao.createEmployee("Neha");

        // 2. Create Projects
        dao.createProject("Banking App", "HDFC");
        dao.createProject("Ecommerce Platform", "Amazon");
        dao.createProject("Healthcare Portal", "Apollo");

        // 3. Assign Employees to Projects
        dao.assignEmployeeToProject(1, 1);
        dao.assignEmployeeToProject(1, 2);

        dao.assignEmployeeToProject(2, 1);
        dao.assignEmployeeToProject(3, 3);

        // 4. View Projects of an Employee
        System.out.println("\nProjects of Employee 1:");
        dao.getProjectsOfEmployee(1);

        // 5. View Employees working on a Project
        System.out.println("\nEmployees working on Project 1:");
        dao.getEmployeesOfProject(1);

        // 6. Employees working on multiple projects
        System.out.println("\nEmployees working on multiple projects:");
        dao.getEmployeesWorkingOnMultipleProjects();

        // 7. Project workload report
        System.out.println("\nProject Workload Report:");
        dao.getProjectWorkload();

        // 8. Remove employee from project
        System.out.println("\nRemoving Employee 1 from Project 2:");
        dao.removeEmployeeFromProject(1, 2);

        // 9. View updated projects
        System.out.println("\nUpdated Projects of Employee 1:");
        dao.getProjectsOfEmployee(1);

    }
}