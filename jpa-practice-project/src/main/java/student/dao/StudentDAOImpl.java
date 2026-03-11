package student.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import student.entity.Laptop;
import student.entity.Student;
import student.repository.JPAUtil;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void saveData() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Laptop firstLaptop = new Laptop();
            firstLaptop.setBrand("HP");
            firstLaptop.setPrice(75000);

            Laptop secondLaptop = new Laptop();
            secondLaptop.setBrand("Lenovo");
            secondLaptop.setPrice(82000);

            Student student = new Student();
            student.setName("Sourav");
            student.setCourse("Computer Science");
            student.setLaptop(firstLaptop);
            firstLaptop.setStudent(student);

            em.persist(firstLaptop);
            em.persist(secondLaptop);
            em.persist(student);

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

    @Override
    public void fetchLaptopFromStudent(int studentId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Student student = em.find(Student.class, studentId);
            if (student == null || student.getLaptop() == null) {
                System.out.println("Student or laptop not found.");
                return;
            }
            System.out.println(student.getLaptop().getBrand());
        } finally {
            em.close();
        }
    }

    @Override
    public void fetchStudentFromLaptop(long laptopId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Laptop laptop = em.find(Laptop.class, laptopId);
            if (laptop == null || laptop.getStudent() == null) {
                System.out.println("Laptop or student not found.");
                return;
            }
            System.out.println(laptop.getStudent().getName());
        } finally {
            em.close();
        }
    }

    @Override
    public void updateLaptopBrand(long laptopId, String newBrand) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Laptop laptop = em.find(Laptop.class, laptopId);
            if (laptop != null) {
                laptop.setBrand(newBrand);
                System.out.println("Laptop brand updated successfully.");
            } else {
                System.out.println("Laptop not found.");
            }
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

    @Override
    public void updateStudentCourse(int studentId, String newCourse) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, studentId);
            if (student != null) {
                student.setCourse(newCourse);
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("Student not found.");
            }
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

    @Override
    public void changeLaptopForStudent(int studentId, long newLaptopId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student fetchedStudent = em.find(Student.class, studentId);
            Laptop newLaptop = em.find(Laptop.class, newLaptopId);

            if (fetchedStudent == null || newLaptop == null) {
                System.out.println("Student or laptop not found.");
                tx.rollback();
                return;
            }

            Laptop oldLaptop = fetchedStudent.getLaptop();
            if (oldLaptop != null) {
                oldLaptop.setStudent(null);
            }

            fetchedStudent.setLaptop(newLaptop);
            newLaptop.setStudent(fetchedStudent);

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

    @Override
    public void removeLaptopFromStudent(int studentId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, studentId);
            if (student == null) {
                System.out.println("Student not found.");
                tx.rollback();
                return;
            }

            Laptop laptop = student.getLaptop();
            if (laptop == null) {
                System.out.println("Student has no laptop assigned.");
                tx.rollback();
                return;
            }

            student.setLaptop(null);
            laptop.setStudent(null);
            tx.commit();
            System.out.println("Laptop removed successfully from student.");
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
    public void deleteStudentAndLaptop(int studentId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, studentId);
            if (student == null) {
                System.out.println("Student not found.");
                tx.rollback();
                return;
            }

            Laptop laptop = student.getLaptop();
            if (laptop != null) {
                student.setLaptop(null);
                laptop.setStudent(null);
            }

            em.remove(student);
            if (laptop != null) {
                em.remove(laptop);
            }
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
}
