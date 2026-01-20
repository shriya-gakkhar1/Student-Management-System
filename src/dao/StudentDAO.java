package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Student;

public class StudentDAO {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "shri@9414";

    private Connection getConnection() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("MySQL JDBC Driver not found", e);
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // ADD STUDENT
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (id, name, age, course) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourse());

            ps.executeUpdate();
        }
    }

    // GET ALL STUDENTS
    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course")
                ));
            }
        }
        return list;
    }

    // FIND BY ID
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
            }
        }
        return student;
    }

    // DELETE STUDENT
    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE STUDENT
public boolean updateStudent(Student student) throws SQLException {
    String sql = "UPDATE students SET name = ?, age = ?, course = ? WHERE id = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, student.getName());
        ps.setInt(2, student.getAge());
        ps.setString(3, student.getCourse());
        ps.setInt(4, student.getId());

        return ps.executeUpdate() > 0;
    }
}

// GET SORTED STUDENTS
public ArrayList<Student> getSortedStudents(String column, String order) throws SQLException {

    ArrayList<Student> list = new ArrayList<>();

    // ⚠️ Prevent SQL injection by allowing only known columns
    if (!(column.equals("id") || column.equals("name") || column.equals("age"))) {
        throw new SQLException("Invalid sort column");
    }

    String sql = "SELECT * FROM students ORDER BY " + column + " " + order;

    try (Connection con = getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course")
            ));
        }
    }
    return list;
}

}