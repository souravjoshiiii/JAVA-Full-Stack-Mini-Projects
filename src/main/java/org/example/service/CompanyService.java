package org.example.service;

import org.example.DAO.CompanyDAO;
import org.example.DAO.CompanyDAOImpl;
import org.example.repository.JPAUtil;

public class CompanyService {
    public static void main(String[] args) {
        CompanyDAO dao = new CompanyDAOImpl();
        try {
            dao.createDepartment("IT", "Delhi");
            dao.createDepartment("HR", "Mumbai");

            dao.addEmployee("Rahul", 60000, 1);
            dao.addEmployee("Aman", 55000, 1);
            dao.addEmployee("Neha", 50000, 2);

            System.out.println("\nEmployees in IT Department:");
            dao.getEmployeesByDepartment(1);

            System.out.println("\nEmployee Department Details:");
            dao.getEmployeeDepartment(1);

            System.out.println("\nTransferring Employee 1 to HR...");
            dao.transferEmployee(1, 2);

            System.out.println("\nEmployee Department After Transfer:");
            dao.getEmployeeDepartment(1);

            System.out.println("\nDeleting Employee with ID 3...");
            dao.deleteEmployee(3);

            System.out.println("\nEmployees in HR Department:");
            dao.getEmployeesByDepartment(2);
        } finally {
            JPAUtil.close();
        }
    }
}
