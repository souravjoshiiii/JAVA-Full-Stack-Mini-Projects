package org.example.DAO;

public interface CompanyDAO {

    void createDepartment(String name, String location);

    void addEmployee(String name, double salary, int deptId);

    void transferEmployee(int empId, int newDeptId);

    void getEmployeeDepartment(int empId);

    void getEmployeesByDepartment(int deptId);

    void deleteEmployee(int empId);
}