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

        // Panel von Tasten
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

    //   Methode zum Stylen von Komponenten
    private JLabel createStyledLabel(String text, Color color, Font font) {
        // Erstellt ein stilisiertes Label
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        return label;
    }
    //   Erstellt ein stilisiertes Textfeld
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
    //   Erstellt einen stilisierten Button
    private JButton createStyledButton(String text, Color bgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    //   Methode zum Hinzüfugen der Beispiele
    private void addSampleData() {
        students.add(new Student(1, "Ali Rezaei", 20));
    }
    //   Hauptmethoden :
    private void saveToFile() {
        // Speichert die Daten in einer Datei
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                String line = student.id + "," + student.name + "," + student.age;
                writer.write(line);    // نوشتن خط
                writer.newLine();      // رفتن به خط جدید
            }
            writer.flush(); // اطمینان از نوشتن همه داده‌ها
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + DATA_FILE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }
    // Lädt die Daten aus einer Datei
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists())  return;

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            students.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        int age = Integer.parseInt(parts[2]);
                        students.add(new Student(id, name, age));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Data loaded successfully from " + DATA_FILE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Fügt einen neuen Studenten hinzu
    private void addStudent() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            // تبدیل ID به int
            int id = Integer.parseInt(idText);
            int age = Integer.parseInt(ageText);

            // بررسی محدوده ID
            if (id <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive ID!");
                return;
            }

            if (age <= 0 || age > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (1-100)!");
                return;
            }
            // بررسی تکراری نبودن ID
            for (Student student : students) {
                if (student.id == id) {
                    JOptionPane.showMessageDialog(this, "Student with this ID already exists!");
                    return;
                }
            }

            students.add(new Student(id, name, age));
            refreshDisplay();
            clearFields();
            JOptionPane.showMessageDialog(this, "Student added successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for age and ID!");
        }
    }
//    löscht einen Student
    private void deleteStudent() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student ID to delete!");
            return;
        }
        try {
            int id = Integer.parseInt(idText);  // تبدیل به int
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete student with ID: " + id + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                boolean removed = students.removeIf(student -> student.id == id);  // تغییر به ==

                if (removed) {
                    refreshDisplay();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Student with ID " + id + " not found!");
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for ID!");
        }
    }

    // Aktualisiert die Anzeige mit allen Studenten
    private void refreshDisplay() {
        if (students.isEmpty()) {
            displayArea.setText("No students available.");
            return;
        }
        /* sb ist hier wie ein Fabrik von String */
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total students : %d\n\n", students.size()));
        sb.append(String.format("%-5s %-20s %-5s\n", "ID", "Name", "Age"));
        sb.append("----------------------------------------\n");

        for (Student student : students) {
            sb.append(String.format("%-5d %-20s %-5d\n",
                    student.id, student.name, student.age));
        }

        displayArea.setText(sb.toString());
    }
    // Löscht die Eingabefelder
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        idField.requestFocus();
    }
    // Hauptmethode zum Starten
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagement gui = new StudentManagement();
            gui.setVisible(true);
        });
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