package org.example.dao;

public interface CompanyDAO {
    void createDepartment(String name, String location);

    void addEmployeeToDepartment(int deptId, String name, double salary);

    void getDepartmentWithEmployees(int deptId);

    void transferEmployee(int empId, int newDeptId);

    void removeEmployeeFromDepartment(int empId);

    void deleteDepartment(int deptId);

    void getDepartmentsWithEmployeeCount();

    void getHighSalaryEmployees(double salary);

    void moveAllEmployees(int fromDept, int toDept);

    void getDepartmentsWithPagination(int page, int size);
}
