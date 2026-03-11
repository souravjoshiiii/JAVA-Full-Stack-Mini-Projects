package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.repository.JPAUtil;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    @Override
    public void createDepartment(String name, String location) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Department department = new Department();
            department.setName(name);
            department.setLocation(location);
            em.persist(department);

            tx.commit();
            System.out.println("Department saved successfully");
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void addEmployee(String name, double salary, int deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Department dept = em.find(Department.class, deptId);
            if (dept == null) {
                System.out.println("Department not found");
                tx.rollback();
                return;
            }

            Employee emp = new Employee();
            emp.setName(name);
            emp.setSalary(salary);
            emp.setDepartment(dept);
            em.persist(emp);

            tx.commit();
            System.out.println("Employee saved successfully");
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void transferEmployee(int empId, int deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee emp = em.find(Employee.class, empId);
            Department department = em.find(Department.class, deptId);
            if (emp == null || department == null) {
                System.out.println("Employee or department not found");
                tx.rollback();
                return;
            }

            emp.setDepartment(department);
            tx.commit();
            System.out.println("Employee transferred successfully");
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void getEmployeeDepartment(int empId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Employee emp = em.find(Employee.class, empId);
            if (emp == null) {
                System.out.println("Employee not found");
                return;
            }

            Department dept = emp.getDepartment();
            System.out.println("Employee Name: " + emp.getName());
            if (dept == null) {
                System.out.println("Department: not assigned");
                return;
            }

            System.out.println("Department: " + dept.getName());
        } finally {
            em.close();
        }
    }

    @Override
    public void getEmployeesByDepartment(int deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Employee> list = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.department.id = :deptId",
                    Employee.class
            ).setParameter("deptId", deptId).getResultList();

            if (list.isEmpty()) {
                System.out.println("No employees found for department " + deptId);
                return;
            }

            for (Employee employee : list) {
                System.out.println(employee.getName() + " Salary - " + employee.getSalary());
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteEmployee(int empId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee emp = em.find(Employee.class, empId);
            if (emp == null) {
                System.out.println("Employee not found");
                tx.rollback();
                return;
            }

            em.remove(emp);
            tx.commit();
            System.out.println("Employee deleted successfully");
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
