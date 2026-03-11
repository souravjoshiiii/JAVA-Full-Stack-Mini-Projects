package student;

import student.dao.StudentDAO;
import student.dao.StudentDAOImpl;
import student.repository.JPAUtil;

public class StudentLaptopService {

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAOImpl();
        try {
            dao.saveData();
            dao.fetchLaptopFromStudent(1);
            dao.updateLaptopBrand(1L, "Dell");
            dao.updateStudentCourse(1, "AI & ML");
            dao.changeLaptopForStudent(1, 2L);
            dao.removeLaptopFromStudent(1);
            dao.deleteStudentAndLaptop(1);
        } finally {
            JPAUtil.close();
        }
    }
}
