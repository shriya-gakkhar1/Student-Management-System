package util;

import model.Student;
import java.io.*;
import java.util.ArrayList;

public class FileUtil {

    private static final String FILE_PATH = "data/students.txt";

   
    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String course = data[3];

                students.add(new Student(id, name, age, course));
            }
        } catch (IOException e) {
           
        }

        return students;
    }

    
    public static void saveStudents(ArrayList<Student> students) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Student s : students) {
                bw.write(
                        s.getId() + "," +
                        s.getName() + "," +
                        s.getAge() + "," +
                        s.getCourse()
                );
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }
}
