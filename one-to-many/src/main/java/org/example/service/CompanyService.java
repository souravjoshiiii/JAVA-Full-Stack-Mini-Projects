package org.example.service;

import org.example.dao.CompanyDAO;
import org.example.dao.CompanyDAOImpl;

public class CompanyService {

    public static void main(String[] args) {

        CompanyDAO dao =new CompanyDAOImpl();

        // 1. Create departments
        dao.createDepartment("IT", "Delhi");
        dao.createDepartment("HR", "Mumbai");
        dao.createDepartment("Finance", "Bangalore");

        // 2. Add employees to departments
        dao.addEmployeeToDepartment(1, "Rahul", 60000);
        dao.addEmployeeToDepartment(1, "Aman", 55000);
        dao.addEmployeeToDepartment(2, "Neha", 50000);
        dao.addEmployeeToDepartment(3, "Rohit", 70000);

        // 3. Fetch department with employees (uses JOIN FETCH)
        System.out.println("\n--- Department With Employees ---");
        dao.getDepartmentWithEmployees(1);

        // 4. Transfer employee from one department to another
        System.out.println("\n--- Transfer Employee ---");
        dao.transferEmployee(2, 2); // employee 2 moves to HR

        // 5. Remove employee from department
        System.out.println("\n--- Remove Employee From Department ---");
        dao.removeEmployeeFromDepartment(3);

        // 6. Department employee statistics
        System.out.println("\n--- Department Employee Count ---");
        dao.getDepartmentsWithEmployeeCount();

        // 7. Employees with high salary
        System.out.println("\n--- High Salary Employees ---");
        dao.getHighSalaryEmployees(60000);

        // 8. Move all employees between departments
        System.out.println("\n--- Move Employees From IT to Finance ---");
        dao.moveAllEmployees(1, 3);

        // 9. Pagination example
        System.out.println("\n--- Department Pagination ---");
        dao.getDepartmentsWithPagination(0, 2);

        // 10. Delete department
        System.out.println("\n--- Delete Department ---");
        dao.deleteDepartment(3);
    }
}