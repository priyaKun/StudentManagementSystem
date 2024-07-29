import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentManagementSystemGUI extends JFrame implements ActionListener {
    private StudentManagementSystem sms;
    private JTextField nameField, rollNumberField, gradeField;
    private JTextArea outputArea;

    public StudentManagementSystemGUI(StudentManagementSystem sms) {
        this.sms = sms;

        setTitle("Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Roll Number
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(rollNumberLabel, gbc);

        rollNumberField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(rollNumberField, gbc);

        // Grade
        JLabel gradeLabel = new JLabel("Grade:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(gradeLabel, gbc);

        gradeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(gradeField, gbc);

        // Buttons
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addButton, gbc);

        JButton removeButton = new JButton("Remove Student");
        removeButton.addActionListener(this);
        gbc.gridx = 1;
        panel.add(removeButton, gbc);

        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(this);
        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        JButton displayButton = new JButton("Display All Students");
        displayButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(displayButton, gbc);

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(saveButton, gbc);

        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(this);
        gbc.gridx = 1;
        panel.add(loadButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        gbc.gridx = 2;
        panel.add(exitButton, gbc);

        // Output Area
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        panel.add(scrollPane, gbc);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Student":
                addStudent();
                break;
            case "Remove Student":
                removeStudent();
                break;
            case "Search Student":
                searchStudent();
                break;
            case "Display All Students":
                displayAllStudents();
                break;
            case "Save to File":
                saveData();
                break;
            case "Load from File":
                loadData();
                break;
            case "Exit":
                exitApplication();
                break;
        }
    }

    private void addStudent() {
        String name = nameField.getText();
        int rollNumber = getIntFromField(rollNumberField);
        String grade = gradeField.getText();

        if (name.isEmpty() || rollNumber == -1 || grade.isEmpty()) {
            outputArea.setText("Please fill in all fields with valid data.");
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
        outputArea.setText("Student added successfully.");
    }

    private void removeStudent() {
        int rollNumber = getIntFromField(rollNumberField);

        if (rollNumber == -1) {
            outputArea.setText("Please enter a valid roll number.");
            return;
        }

        sms.removeStudent(rollNumber);
    }

    private void searchStudent() {
        int rollNumber = getIntFromField(rollNumberField);

        if (rollNumber == -1) {
            outputArea.setText("Please enter a valid roll number.");
            return;
        }

        Student foundStudent = sms.findStudent(rollNumber);
        if (foundStudent != null) {
            outputArea.setText("Student found: " + foundStudent);
        } else {
            outputArea.setText("Student not found.");
        }
    }

    private void displayAllStudents() {
        List<Student> allStudents = sms.getAllStudents();
        if (allStudents.isEmpty()) {
            outputArea.setText("No students in the system.");
        } else {
            outputArea.setText("All Students:\n");
            for (Student s : allStudents) {
                outputArea.append(s + "\n");
            }
        }
    }

    private void saveData() {
        sms.saveToFile("students.dat");
    }

    private void loadData() {
        sms.loadFromFile("students.dat");
    }

    private void exitApplication() {
        System.exit(0);
    }

    private int getIntFromField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        StudentManagementSystemGUI gui = new StudentManagementSystemGUI(sms);
        gui.setVisible(true);
    }
}
