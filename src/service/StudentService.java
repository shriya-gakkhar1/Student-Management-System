package service;

import dao.StudentDAO;
import exception.InvalidStudentException;
import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentService {

    private StudentDAO dao;

    public StudentService() {
        dao = new StudentDAO();
    }

    // ADD STUDENT
    public void addStudent(Student student) throws InvalidStudentException {

        if (student.getId() <= 0) {
            throw new InvalidStudentException("ID must be greater than 0.");
        }

        if (student.getAge() <= 0) {
            throw new InvalidStudentException("Age must be greater than 0.");
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidStudentException("Name cannot be empty.");
        }

        try {
            if (dao.getStudentById(student.getId()) != null) {
                throw new InvalidStudentException("Student ID already exists.");
            }
            dao.addStudent(student);
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new RuntimeException("Database error while adding student.");
        }
    }

    // VIEW ALL STUDENTS
    public ArrayList<Student> viewStudents() {
        try {
            return dao.getAllStudents();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching students.");
        }
    }

    // SEARCH BY ID
    public Student searchStudent(int id) {
        try {
            return dao.getStudentById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while searching student.");
        }
    }

    // DELETE STUDENT
    public boolean deleteStudent(int id) {
        try {
            return dao.deleteStudent(id);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while deleting student.");
        }
    }

    // UPDATE STUDENT
public boolean updateStudent(int id, String name, int age, String course) throws InvalidStudentException {

    if (age <= 0) {
        throw new InvalidStudentException("Age must be greater than 0.");
    }

    if (name == null || name.trim().isEmpty()) {
        throw new InvalidStudentException("Name cannot be empty.");
    }

    try {
        Student existing = dao.getStudentById(id);
        if (existing == null) {
            return false;
        }

        Student updated = new Student(id, name, age, course);
        return dao.updateStudent(updated);

    } catch (SQLException e) {
        throw new RuntimeException("Database error while updating student.");
    }
}


    // SORT STUDENTS
public ArrayList<Student> sortStudents(int choice) {

    String column;
    String order = "ASC";

    switch (choice) {
        case 1 -> column = "name";
        case 2 -> column = "age";
        case 3 -> column = "id";
        default -> throw new RuntimeException("Invalid sort choice");
    }

    try {
        return dao.getSortedStudents(column, order);
    } catch (SQLException e) {
        throw new RuntimeException("Database error while sorting students");
    }
}

}
   