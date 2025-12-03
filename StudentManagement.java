import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Hauptklasse für das GUI-Fenster
public class StudentManagement extends JFrame {
    private List<Student> students;
    private JTextField idField, nameField, ageField;
    private JTextArea displayArea;
    private JButton deleteButton , clearButton , saveButton , loadButton , addButton;
    private final String DATA_FILE = "students.txt";

    // Farbdefinitionen
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Color ACCENT_COLOR = new Color(255, 140, 0);
    private final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private final Color DANGER_COLOR = new Color(178, 34, 34);
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    // Schriftarten
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 14);

    // Innerliche Klasse für Student
    private static class Student {
        int id;
        String name;
        int age;

        Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}