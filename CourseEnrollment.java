import java.util.Scanner;

// Class to represent a course with ID, name, and credits
class Course {
    private int courseID;
    private String courseName;
    private int credits;

    public Course(int courseID, String courseName, int credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "CourseID: " + courseID + ", Name: " + courseName + ", Credits: " + credits;
    }
}

// Class to manage student enrollments in courses
class Enrollment {
    private int[][] studentCourses; // 2D array to store student-course enrollments
    private int[] count; // Array to keep track of the number of courses each student is enrolled in

    public Enrollment(int numStudents, int numCourses) {
        studentCourses = new int[numStudents][numCourses];
        count = new int[numStudents];
    }

    // Method to check if a student is already enrolled in a course
    private boolean isEnrolled(int studentID, int courseID) {
        for (int i = 0; i < count[studentID]; i++) {
            if (studentCourses[studentID][i] == courseID) {
                return true;
            }
        }
        return false;
    }

    // Method to enroll a student in a course
    public void enroll(int studentID, int courseID, Course[] courseCatalog) {
        if (isEnrolled(studentID, courseID)) {
            System.out.println("Student " + studentID + " is already enrolled in Course ID " + courseID);
        } else {
            studentCourses[studentID][count[studentID]] = courseID;
            count[studentID]++;
            System.out.println("Enrolled successfully!");
            getEnrolledCourses(studentID, courseCatalog); // Show enrolled courses
        }
    }

    // Method to drop a course for a student
    public void drop(int studentID, int courseID, Course[] courseCatalog) {
        if (!isEnrolled(studentID, courseID)) {
            System.out.println("Student " + studentID + " is not enrolled in Course ID " + courseID);
        } else {
            for (int i = 0; i < count[studentID]; i++) {
                if (studentCourses[studentID][i] == courseID) {
                    studentCourses[studentID][i] = studentCourses[studentID][count[studentID] - 1];
                    count[studentID]--;
                    System.out.println("Dropped successfully!");
                    getEnrolledCourses(studentID, courseCatalog); // Show enrolled courses
                    break;
                }
            }
        }
    }

    // Method to get all courses a student is enrolled in
    public void getEnrolledCourses(int studentID, Course[] courseCatalog) {
        System.out.println("Student " + studentID + " is enrolled in the following courses:");
        for (int i = 0; i < count[studentID]; i++) {
            int courseID = studentCourses[studentID][i];
            for (Course course : courseCatalog) {
                if (course.getCourseID() == courseID) {
                    System.out.println(course);
                    break;
                }
            }
        }
    }

    // Method to display all available courses
    public void displayCourses(Course[] courseCatalog) {
        System.out.println("Available courses:");
        for (Course course : courseCatalog) {
            System.out.println(course);
        }
    }
}

// Main class to run the course enrollment system
public class CourseEnrollment {
    public static void main(String[] args) {
        Course[] courseCatalog = {
            new Course(0, "Math", 3),
            new Course(1, "English", 2),
            new Course(2, "History", 4),
            new Course(3, "Science", 3)
        };

        Enrollment enrollment = new Enrollment(10, 10); // Example: 10 students, 10 courses
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Enroll in a Course");
            System.out.println("2. Drop a Course");
            System.out.println("3. View Enrolled Courses");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    enrollment.displayCourses(courseCatalog);
                    System.out.print("Enter Student ID (0-9): ");
                    int studentID = scanner.nextInt();
                    if (studentID < 0 || studentID >= 10) {
                        System.out.println("Invalid Student ID. Please try again.");
                        break;
                    }
                    System.out.print("Enter Course ID: ");
                    int courseID = scanner.nextInt();
                    enrollment.enroll(studentID, courseID, courseCatalog);
                    break;
                case 2:
                    enrollment.displayCourses(courseCatalog);
                    System.out.print("Enter Student ID (0-9): ");
                    studentID = scanner.nextInt();
                    if (studentID < 0 || studentID >= 10) {
                        System.out.println("Invalid Student ID. Please try again.");
                        break;
                    }
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    enrollment.drop(studentID, courseID, courseCatalog);
                    break;
                case 3:
                    System.out.print("Enter Student ID (0-9): ");
                    studentID = scanner.nextInt();
                    if (studentID < 0 || studentID >= 10) {
                        System.out.println("Invalid Student ID. Please try again.");
                        break;
                    }
                    enrollment.getEnrolledCourses(studentID, courseCatalog);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.out.println("23ce145");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
