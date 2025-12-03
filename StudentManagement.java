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

    // Konstruktor
    public StudentManagement() {
        students = new ArrayList<>();

        setTitle("Student Management System");
        setSize(580, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        loadFromFile();
    }

    // Initialisierung der GUI-Komponenten
    private void initializeComponents() {
        // Hauptpanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0,15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(PRIMARY_COLOR);
        // Oberpanel(input , button)
        JPanel topPanel = new JPanel(new BorderLayout(0,8));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        "Student Information"
                ));

        // Panel von Eingabe
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        inputPanel.add(createStyledLabel("Student ID: ", PRIMARY_COLOR, titleFont));
        idField = createStyledTextField(mainFont);
        inputPanel.add(idField);

        inputPanel.add(createStyledLabel("Full Name: ", PRIMARY_COLOR, titleFont));
        nameField = createStyledTextField(mainFont);
        inputPanel.add(nameField);

        inputPanel.add(createStyledLabel("Age: ",PRIMARY_COLOR, titleFont));
        ageField = createStyledTextField(mainFont);
        inputPanel.add(ageField);

        topPanel.add(inputPanel , BorderLayout.NORTH);

        // panel von Tasten
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setBackground(new Color(253, 253, 253));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addButton = createStyledButton("Add Student", SUCCESS_COLOR, mainFont);
        deleteButton = createStyledButton("Delete Student", DANGER_COLOR, mainFont);
        clearButton = createStyledButton("Clear Fields", ACCENT_COLOR, mainFont);
        saveButton = createStyledButton("Save to File", PRIMARY_COLOR, mainFont);
        loadButton = createStyledButton("Load from File", new Color(75, 0, 130), mainFont);
        // Aktionen von Tasten
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel ,BorderLayout.NORTH );

        // Anzeigebereich für die Studentenliste
        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        displayArea.setBackground(new Color(253, 253, 253));
        displayArea.setForeground(TEXT_COLOR);
        displayArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(252, 252, 252), 1),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        // Scrollen für Anzeigebereich
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        "Students List"),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        mainPanel.add(scrollPane , BorderLayout.CENTER);
        // Einbauen von Hauptpanel dem Hauptfenster
        add(mainPanel);
    }

    // Methode zum Stylen von Komponenten
    private JLabel createStyledLabel(String text, Color color, Font font) {
        // Erstellt ein stilisiertes Label
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        return label;
    }
    // Erstellt ein stilisiertes Textfeld
    private JTextField createStyledTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(166, 166, 166), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    // Erstellt einen stilisierten Button
    private JButton createStyledButton(String text, Color bgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }













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