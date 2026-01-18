package service;
import exception.InvalidStudentException;


import model.Student;
import util.FileUtil;

import java.util.ArrayList;

public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();

    public StudentService() {
        students = FileUtil.loadStudents();
    }


    

    
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

    if (searchStudent(student.getId()) != null) {
        throw new InvalidStudentException("Student ID already exists.");
    }

    students.add(student);
}


   
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

   
    public Student searchStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

  
    public boolean updateStudent(int id, String name, int age, String course) {
        Student s = searchStudent(id);
        if (s != null) {
            s.setName(name);
            s.setAge(age);
            s.setCourse(course);
            return true;
        }
        return false;
    }

    
    public boolean deleteStudent(int id) {
        Student s = searchStudent(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    public ArrayList<Student> getStudents() {
    return students;
}

}
