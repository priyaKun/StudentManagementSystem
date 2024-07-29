import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        boolean removed = students.removeIf(student -> student.getRollNumber() == rollNumber);
        if (!removed) {
            System.out.println("No student found with roll number: " + rollNumber);
        } else {
            System.out.println("Student removed successfully.");
        }
    }

    public Student findStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data to file.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Data loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file.");
            e.printStackTrace();
        }
    }
}

public class StudentManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    saveData();
                    break;
                case 6:
                    loadData();
                    break;
                case 7:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit");
        System.out.print("Select an option: ");
    }

    private static int getUserChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                return choice;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        int rollNumber = getValidInteger();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void removeStudent() {
        System.out.print("Enter roll number to remove: ");
        int rollNumber = getValidInteger();
        sms.removeStudent(rollNumber);
    }

    private static void searchStudent() {
        System.out.print("Enter roll number to search: ");
        int rollNumber = getValidInteger();
        Student foundStudent = sms.findStudent(rollNumber);
        if (foundStudent != null) {
            System.out.println("Student found: " + foundStudent);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents() {
        List<Student> allStudents = sms.getAllStudents();
        if (allStudents.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("All Students:");
            for (Student s : allStudents) {
                System.out.println(s);
            }
        }
    }

    private static void saveData() {
        sms.saveToFile("students.dat");
    }

    private static void loadData() {
        sms.loadFromFile("students.dat");
    }

    private static void exitApplication() {
        System.out.println("Exiting application.");
        scanner.close();
        System.exit(0);
    }

    private static int getValidInteger() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
