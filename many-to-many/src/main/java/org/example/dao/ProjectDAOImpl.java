package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.Employee;
import org.example.entity.Project;
import org.example.repository.JPAUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ProjectDAOImpl implements ProjectDAO {

    @Override
    public Employee createEmployee(String name) {
        validateText(name, "Employee name");
        Employee employee = new Employee();
        employee.setName(name.trim());
        persist(employee);
        return employee;
    }

    @Override
    public Project createProject(String title, String client) {
        validateText(title, "Project title");
        validateText(client, "Project client");

        Project project = new Project();
        project.setTitle(title.trim());
        project.setClient(client.trim());
        persist(project);
        return project;
    }

    @Override
    public void assignEmployeeToProject(int empId, int projectId) {
        executeInTransaction(em -> {
            Employee employee = requireEntity(em, Employee.class, empId, "employee");
            Project project = requireEntity(em, Project.class, projectId, "project");
            employee.addProject(project);
            return null;
        });
    }

    @Override
    public List<Project> getProjectsOfEmployee(int empId) {
        return executeRead(em -> em.createQuery(
                        "select p from Employee e join e.projects p where e.id = :empId order by p.id",
                        Project.class)
                .setParameter("empId", empId)
                .getResultList());
    }

    @Override
    public List<Employee> getEmployeesOfProject(int projectId) {
        return executeRead(em -> em.createQuery(
                        "select e from Project p join p.employees e where p.id = :projectId order by e.id",
                        Employee.class)
                .setParameter("projectId", projectId)
                .getResultList());
    }

    @Override
    public void removeEmployeeFromProject(int empId, int projectId) {
        executeInTransaction(em -> {
            Employee employee = requireEntity(em, Employee.class, empId, "employee");
            Project project = requireEntity(em, Project.class, projectId, "project");
            employee.removeProject(project);
            return null;
        });
    }

    @Override
    public List<Employee> getEmployeesWorkingOnMultipleProjects() {
        return executeRead(em -> em.createQuery(
                        "select e from Employee e where size(e.projects) > 1 order by e.id",
                        Employee.class)
                .getResultList());
    }

    @Override
    public List<String> getProjectWorkload() {
        return executeRead(em -> em.createQuery(
                        "select p.title, count(e) from Project p left join p.employees e group by p.id, p.title order by p.id",
                        Object[].class)
                .getResultList()
                .stream()
                .map(row -> row[0] + ":-" + row[1])
                .toList());
    }

    @Override
    public void deleteAll() {
        executeInTransaction(em -> {
            em.createNativeQuery("DELETE FROM employee_project").executeUpdate();
            em.createQuery("delete from Employee").executeUpdate();
            em.createQuery("delete from Project").executeUpdate();
            return null;
        });
    }

    private void persist(Object entity) {
        executeInTransaction(em -> {
            em.persist(entity);
            return null;
        });
    }

    private <T> T executeRead(Function<EntityManager, T> action) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            return action.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    private <T> T executeInTransaction(Function<EntityManager, T> action) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T result = action.apply(entityManager);
            transaction.commit();
            return result;
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    private <T> T requireEntity(EntityManager entityManager, Class<T> type, int id, String label) {
        T entity = entityManager.find(type, id);
        if (entity == null) {
            throw new IllegalArgumentException("No " + label + " found with id " + id);
        }
        return entity;
    }

    private void validateText(String value, String label) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
    }
}
