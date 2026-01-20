package ui;

import model.Student;
import service.StudentService;
import exception.InvalidStudentException;

import javax.swing.*;
import java.awt.*;


public class StudentManagementUI extends JFrame {

    private static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);


    private StudentService service;

    public StudentManagementUI() {
        service = new StudentService();

        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("Student Management System", SwingConstants.CENTER);
        heading.setFont(TITLE_FONT);
        add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));


        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View All Students");
        JButton searchBtn = new JButton("Search Student");
        JButton updateBtn = new JButton("Update Student");
        JButton deleteBtn = new JButton("Delete Student");

        addBtn.setFont(UI_FONT);
        viewBtn.setFont(UI_FONT);
        searchBtn.setFont(UI_FONT);
        updateBtn.setFont(UI_FONT);
        deleteBtn.setFont(UI_FONT); 

        panel.add(addBtn);
        panel.add(viewBtn);
        panel.add(searchBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        add(panel, BorderLayout.CENTER);

        // Button Actions
        addBtn.addActionListener(e -> openAddStudentDialog());

        viewBtn.addActionListener(e -> openViewStudentsWindow());

        searchBtn.addActionListener(e -> openSearchStudentDialog());

        deleteBtn.addActionListener(e -> openDeleteStudentDialog());

        updateBtn.addActionListener(e -> openUpdateStudentDialog());




    }
    // Add Student
    private void openAddStudentDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField courseField = new JTextField();

        Object[] fields = {
                "ID:", idField,
                "Name:", nameField,
                "Age:", ageField,
                "Course:", courseField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add Student",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String course = courseField.getText();

                Student student = new Student(id, name, age, course);
                service.addStudent(student);

                JOptionPane.showMessageDialog(this,
                        "Student added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "ID and Age must be numbers.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);

            } catch (InvalidStudentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // View Students
    private void openViewStudentsWindow() {
    JFrame frame = new JFrame("All Students");
    frame.setSize(600, 300);
    frame.setLocationRelativeTo(this);

    String[] columns = {"ID", "Name", "Age", "Course"};

    var students = service.viewStudents();

    Object[][] data = new Object[students.size()][4];

    for (int i = 0; i < students.size(); i++) {
        data[i][0] = students.get(i).getId();
        data[i][1] = students.get(i).getName();
        data[i][2] = students.get(i).getAge();
        data[i][3] = students.get(i).getCourse();
    }

    JTable table = new JTable(data, columns);
    JScrollPane scrollPane = new JScrollPane(table);
    table.setFont(UI_FONT);
    table.setRowHeight(24);
    table.getTableHeader().setFont(UI_FONT);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setAutoCreateRowSorter(true);



    frame.add(scrollPane);
    frame.setVisible(true);
}

//Search
    private void openSearchStudentDialog() {
    String input = JOptionPane.showInputDialog(
            this,
            "Enter Student ID:",
            "Search Student",
            JOptionPane.QUESTION_MESSAGE
    );

    if (input == null) return;

    try {
        int id = Integer.parseInt(input);
        Student student = service.searchStudent(id);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Student not found.",
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    student.toString(),
                    "Student Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                "Please enter a valid number.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

    // Delete Student
    private void openDeleteStudentDialog() {
    String input = JOptionPane.showInputDialog(
            this,
            "Enter Student ID to delete:",
            "Delete Student",
            JOptionPane.WARNING_MESSAGE
    );

    if (input == null) return;

    try {
        int id = Integer.parseInt(input);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete student ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = service.deleteStudent(id);

            if (deleted) {
                JOptionPane.showMessageDialog(this,
                        "Student deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Student not found.",
                        "Result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                "Please enter a valid numeric ID.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
    // Update Student
    private void openUpdateStudentDialog() {
    String input = JOptionPane.showInputDialog(
            this,
            "Enter Student ID to update:",
            "Update Student",
            JOptionPane.QUESTION_MESSAGE
    );

    if (input == null) return;

    try {
        int id = Integer.parseInt(input);
        Student student = service.searchStudent(id);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Student not found.",
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JLabel idLabel = new JLabel("Student ID: " + id);
        idLabel.setFont(UI_FONT);

        JTextField nameField = new JTextField(student.getName());
        JTextField ageField = new JTextField(String.valueOf(student.getAge()));
        JTextField courseField = new JTextField(student.getCourse());

        Object[] fields = {
        idLabel,
        "Name:", nameField,
        "Age:", ageField,
        "Course:", courseField
};


        int option = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Update Student",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String course = courseField.getText();

            boolean updated = service.updateStudent(id, name, age, course);

            JOptionPane.showMessageDialog(this,
                    updated ? "Student updated successfully." : "Update failed.",
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                "Invalid input.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);

    } catch (InvalidStudentException e) {
        JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }
}




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementUI().setVisible(true);
        });
    }
}
