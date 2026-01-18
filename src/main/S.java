package main;

import service.StudentService;

import model.Student;

import java.util.InputMismatchException;
import java.util.Scanner;

import exception.InvalidStudentException;

public class S{

    private static int readInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                        try {
                        int id;
                        System.out.print("Enter ID: ");
                        while (true) {
                            try {
                                id = sc.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Enter a valid number");
                                sc.nextLine();
                            }
                        }


                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        int age;
                        System.out.print("Enter Age: ");
                        try {
                            age = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Age must be a number.");
                            sc.nextLine();
                            return;
                        }

                        sc.nextLine();
                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        Student s = new Student(id, name, age, course);
                        service.addStudent(s);

                        System.out.println("Student added successfully.");

                    } catch (InvalidStudentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case 2:
                    var students = service.viewStudents();

                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        for (Student s : students) {
                            System.out.println(s);
                        }
                    }
                    break;


                case 3:
                    System.out.print("Enter Student ID to search: ");
                    int searchId = sc.nextInt();
                    Student found = service.searchStudent(searchId);

                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                    case 4:
                    try {
                        int updateId = readInt(sc, "Enter Student ID to update: ");

                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();

                        int newAge = readInt(sc, "Enter New Age: ");

                        sc.nextLine();
                        System.out.print("Enter New Course: ");
                        String newCourse = sc.nextLine();

                        boolean updated = service.updateStudent(updateId, newName, newAge, newCourse);

                        System.out.println(updated
                                ? "Student updated successfully."
                                : "Student not found.");

                    } 
                    catch (InvalidStudentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case 5:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = sc.nextInt();

                    boolean deleted = service.deleteStudent(deleteId);
                    System.out.println(deleted ? "Student deleted successfully." : "Student not found.");
                    break;

                case 6:
                    System.out.println("Exiting application...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
