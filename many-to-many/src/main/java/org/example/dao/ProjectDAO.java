package org.example.dao;

import org.example.entity.Employee;
import org.example.entity.Project;

import java.util.List;

public interface ProjectDAO {

    Employee createEmployee(String name);

    Project createProject(String title, String client);

    void assignEmployeeToProject(int empId, int projectId);

    List<Project> getProjectsOfEmployee(int empId);

    List<Employee> getEmployeesOfProject(int projectId);

    void removeEmployeeFromProject(int empId, int projectId);

    List<Employee> getEmployeesWorkingOnMultipleProjects();

    List<String> getProjectWorkload();

    void deleteAll();

}
