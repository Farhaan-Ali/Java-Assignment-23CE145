import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    private int studentID;
    private String name;
    private int age;
    private String department;

    public Student(int studentID, String name, int age, String department) {
        this.studentID = studentID;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Name: " + name + ", Age: " + age + ", Department: " + department;
    }
}

class StudentRecordSystem {
    private ArrayList<Student> students;

    public StudentRecordSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudent(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

public class StudentRecord {
    public static void main(String[] args) {
        StudentRecordSystem system = new StudentRecordSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. View Student by ID");
            System.out.println("3. Display All Students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the buffer
                continue;
            }

            switch (choice) {
                case 1:
                    int id;
                    String name;
                    int age;
                    String department;

                    try {
                        System.out.print("Enter Student ID: ");
                        id = scanner.nextInt();
                        scanner.nextLine(); // consume the newline

                        System.out.print("Enter Name: ");
                        name = scanner.nextLine();

                        System.out.print("Enter Age: ");
                        age = scanner.nextInt();
                        scanner.nextLine(); // consume the newline

                        System.out.print("Enter Department: ");
                        department = scanner.nextLine();

                        if (age < 0) {
                            System.out.println("Age cannot be negative.");
                            break;
                        }

                        system.addStudent(new Student(id, name, age, department));
                        System.out.println("Student added successfully.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid data.");
                        scanner.nextLine(); // clear the buffer
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID to search: ");
                    try {
                        int searchID = scanner.nextInt();
                        Student student = system.getStudent(searchID);
                        if (student != null) {
                            System.out.println(student);
                        } else {
                            System.out.println("Student not found!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid student ID.");
                        scanner.nextLine(); // clear the buffer
                    }
                    break;

                case 3:
                    system.displayAllStudents();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return; // End the program

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
