package org.example.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.repository.JPAUtil;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    public void createDepartment(String name, String location) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department dept = new Department();
            dept.setName(name);
            dept.setLocation(location);
            em.persist(dept);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void addEmployeeToDepartment(int deptId, String name, double salary) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department dept = requireDepartment(em, deptId);
            Employee emp = new Employee();
            emp.setName(name);
            emp.setSalary(salary);
            dept.addEmployee(emp);
            em.persist(emp);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void getDepartmentWithEmployees(int deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Department dept = requireDepartment(em, deptId);
            List<Employee> list = dept.getEmployees();
            for (Employee emp : list) {
                System.out.println("Employee Name :-" + emp.getName() + " Employee Salary :-" + emp.getSalary());
            }
        } finally {
            em.close();
        }
    }

    public void transferEmployee(int empId, int newDeptId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee emp = requireEmployee(em, empId);
            Department dept = requireDepartment(em, newDeptId);
            emp.setDepartment(dept);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void removeEmployeeFromDepartment(int empId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee emp = requireEmployee(em, empId);
            emp.setDepartment(null);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deleteDepartment(int deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department department = requireDepartment(em, deptId);
            if (!department.getEmployees().isEmpty()) {
                System.out.println("Cannot delete department with employees");
                tx.rollback();
                return;
            }
            em.remove(department);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void getDepartmentsWithEmployeeCount() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Object[]> result = em.createQuery(
                    "SELECT d.name, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.name",
                    Object[].class
            ).getResultList();
            for (Object[] row : result) {
                System.out.println(row[0] + ":" + row[1]);
            }
        } finally {
            em.close();
        }
    }

    public void getHighSalaryEmployees(double salary) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Employee> list = em.createQuery(
                            "SELECT e FROM Employee e WHERE e.salary > :salary",
                            Employee.class)
                    .setParameter("salary", salary)
                    .getResultList();
            list.forEach(e -> System.out.println(e.getName()));
        } finally {
            em.close();
        }
    }

    public void moveAllEmployees(int fromDept, int toDept) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department targetDepartment = requireDepartment(em, toDept);
            int updated = em.createQuery(
                            "UPDATE Employee e SET e.department = :targetDepartment WHERE e.department.id = :fromDept")
                    .setParameter("targetDepartment", targetDepartment)
                    .setParameter("fromDept", fromDept)
                    .executeUpdate();
            tx.commit();
            System.out.println(updated + " Employees moved");
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void getDepartmentsWithPagination(int page, int size) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Department> list = em.createQuery(
                            "SELECT d FROM Department d ORDER BY d.name",
                            Department.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
            list.forEach(d -> System.out.println(d.getName()));
        } finally {
            em.close();
        }
    }

    private Department requireDepartment(EntityManager em, int deptId) {
        Department department = em.find(Department.class, deptId);
        if (department == null) {
            throw new IllegalArgumentException("Department not found: " + deptId);
        }
        return department;
    }

    private Employee requireEmployee(EntityManager em, int empId) {
        Employee employee = em.find(Employee.class, empId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found: " + empId);
        }
        return employee;
    }
}
