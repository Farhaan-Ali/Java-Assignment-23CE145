import java.util.Scanner;

public class StudentGradingSystem {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        GradingManager manager = new GradingManager();

        // Initialize some course credits
        manager.setCourseCredits(101, 3);
        manager.setCourseCredits(102, 4);
        manager.setCourseCredits(103, 2);

        // Main menu loop
        while (true) {
            System.out.println("1. Register Student");
            System.out.println("2. Record Grade");
            System.out.println("3. Compute GPA");
            System.out.println("4. Generate Grade Report");
            System.out.println("5. List All Students");
            System.out.println("6. Find Student by ID");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int choice = inputScanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = inputScanner.nextInt();
                    inputScanner.nextLine(); // consume newline
                    System.out.print("Enter Student Name: ");
                    String studentName = inputScanner.nextLine();
                    System.out.print("Enter Age: ");
                    int studentAge = inputScanner.nextInt();
                    inputScanner.nextLine(); // consume newline
                    System.out.print("Enter Department: ");
                    String studentDepartment = inputScanner.nextLine();
                    manager.registerStudent(new Student(id, studentName, studentAge, studentDepartment));
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    id = inputScanner.nextInt();
                    System.out.print("Enter Course ID: ");
                    int courseID = inputScanner.nextInt();
                    System.out.print("Enter Grade (A/B/C/D/F): ");
                    char studentGrade = inputScanner.next().charAt(0);
                    manager.recordGrade(new Grade(id, courseID, studentGrade));
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    id = inputScanner.nextInt();
                    double gpa = manager.computeGPA(id);
                    System.out.printf("GPA: %.2f%n", gpa);
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    id = inputScanner.nextInt();
                    manager.generateGradeReport(id);
                    break;

                case 5:
                    manager.listAllStudents();
                    break;

                case 6:
                    System.out.print("Enter Student ID: ");
                    id = inputScanner.nextInt();
                    manager.findStudentByID(id);
                    break;

                case 7:
                    System.out.println("Exiting...");
                    inputScanner.close();
                    return;

                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}

class GradingManager {
    private static final int MAX_STUDENTS = 100;
    private static final int MAX_GRADES = 200;
    private static final int MAX_COURSES = 100;

    private Student[] studentArray = new Student[MAX_STUDENTS];
    private Grade[] gradeArray = new Grade[MAX_GRADES];
    private int[] credits = new int[MAX_COURSES];

    private int currentStudentCount = 0;
    private int currentGradeCount = 0;

    public void registerStudent(Student student) {
        if (currentStudentCount < MAX_STUDENTS) {
            for (int i = 0; i < currentStudentCount; i++) {
                if (studentArray[i].getStudentID() == student.getStudentID()) {
                    System.out.println("Student with this ID already exists.");
                    return;
                }
            }
            studentArray[currentStudentCount++] = student;
            System.out.println("Student registered successfully.");
        } else {
            System.out.println("Student limit reached.");
        }
    }

    public void recordGrade(Grade grade) {
        if (currentGradeCount < MAX_GRADES) {
            for (int i = 0; i < currentGradeCount; i++) {
                if (gradeArray[i].getStudentID() == grade.getStudentID() &&
                    gradeArray[i].getCourseID() == grade.getCourseID()) {
                    System.out.println("Grade for this course and student already recorded.");
                    return;
                }
            }
            gradeArray[currentGradeCount++] = grade;
            System.out.println("Grade recorded successfully.");
        } else {
            System.out.println("Grade limit reached.");
        }
    }

    public void setCourseCredits(int courseID, int creditValue) {
        if (courseID >= 0 && courseID < MAX_COURSES) {
            credits[courseID] = creditValue;
        } else {
            System.out.println("Invalid course ID.");
        }
    }

    public double computeGPA(int studentID) {
        int totalCredits = 0;
        int totalPoints = 0;

        for (int i = 0; i < currentGradeCount; i++) {
            Grade grade = gradeArray[i];
            if (grade.getStudentID() == studentID) {
                int courseID = grade.getCourseID();
                if (courseID < MAX_COURSES) {
                    int creditValue = credits[courseID];
                    int gradePoints = convertGradeToPoints(grade.getGrade());

                    totalCredits += creditValue;
                    totalPoints += gradePoints * creditValue;
                }
            }
        }

        return totalCredits == 0 ? 0 : (double) totalPoints / totalCredits;
    }

    private int convertGradeToPoints(char grade) {
        switch (grade) {
            case 'A': return 4;
            case 'B': return 3;
            case 'C': return 2;
            case 'D': return 1;
            case 'F': return 0;
            default: throw new IllegalArgumentException("Unknown grade: " + grade);
        }
    }

    public void generateGradeReport(int studentID) {
        Student student = findStudentByID(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Grade Report for " + student.getName() + ":");
        boolean gradesFound = false;
        for (int i = 0; i < currentGradeCount; i++) {
            Grade grade = gradeArray[i];
            if (grade.getStudentID() == studentID) {
                System.out.println("Course ID: " + grade.getCourseID() + ", Grade: " + grade.getGrade());
                gradesFound = true;
            }
        }

        if (!gradesFound) {
            System.out.println("No grades found for this student.");
        } else {
            double gpa = computeGPA(studentID);
            System.out.printf("GPA: %.2f%n", gpa);
        }
    }

    public void listAllStudents() {
        if (currentStudentCount == 0) {
            System.out.println("No students registered.");
            return;
        }

        System.out.println("Registered Students:");
        for (int i = 0; i < currentStudentCount; i++) {
            Student student = studentArray[i];
            System.out.printf("ID: %d, Name: %s, Age: %d, Department: %s%n",
                student.getStudentID(), student.getName(), student.getAge(), student.getDepartment());
        }
    }

    public void findStudentByID(int studentID) {
        Student student = findStudentByID(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.printf("ID: %d, Name: %s, Age: %d, Department: %s%n",
            student.getStudentID(), student.getName(), student.getAge(), student.getDepartment());
    }

    private Student findStudentByID(int studentID) {
        for (int i = 0; i < currentStudentCount; i++) {
            if (studentArray[i].getStudentID() == studentID) {
                return studentArray[i];
            }
        }
        return null;
    }
}

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
}

class Grade {
    private int studentID;
    private int courseID;
    private char grade;

    public Grade(int studentID, int courseID, char grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public char getGrade() {
        return grade;
    }
}
