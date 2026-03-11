package student.dao;

public interface StudentDAO {

    void saveData();

    void fetchLaptopFromStudent(int studentId);

    void fetchStudentFromLaptop(long laptopId);

    void updateLaptopBrand(long laptopId, String newBrand);

    void updateStudentCourse(int studentId, String newCourse);

    void changeLaptopForStudent(int studentId, long newLaptopId);

    void removeLaptopFromStudent(int studentId);

    void deleteStudentAndLaptop(int studentId);
}
