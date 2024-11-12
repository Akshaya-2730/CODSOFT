import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolled;
    String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    // Check if the course has available slots
    public boolean hasAvailableSlots() {
        return enrolled < capacity;
    }

    // Register a student in the course
    public boolean registerStudent() {
        if (hasAvailableSlots()) {
            enrolled++;
            return true;
        }
        return false;
    }

    // Drop a student from the course
    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    // Display course details
    public void displayCourseInfo() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Capacity: " + capacity);
        System.out.println("Enrolled: " + enrolled);
        System.out.println("Available Slots: " + (capacity - enrolled));
    }
}

class Student {
    String studentId;
    String name;
    ArrayList<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Register a course
    public boolean registerCourse(Course course) {
        if (course.hasAvailableSlots() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.registerStudent();
            return true;
        }
        return false;
    }

    // Drop a course
    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            return true;
        }
        return false;
    }

    // Display registered courses
    public void displayRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("Registered Courses:");
            for (Course course : registeredCourses) {
                System.out.println("- " + course.title + " (" + course.courseCode + ")");
            }
        }
    }
}

class CourseRegistrationSystem {
    HashMap<String, Course> courses;
    HashMap<String, Student> students;

    public CourseRegistrationSystem() {
        courses = new HashMap<>();
        students = new HashMap<>();
    }

    // Add a course to the system
    public void addCourse(Course course) {
        courses.put(course.courseCode, course);
    }

    // Add a student to the system
    public void addStudent(Student student) {
        students.put(student.studentId, student);
    }

    // List available courses
    public void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            course.displayCourseInfo();
            System.out.println();
        }
    }

    // Register a student for a course
    public boolean registerStudentForCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);
        if (student != null && course != null) {
            return student.registerCourse(course);
        }
        return false;
    }

    // Drop a course for a student
    public boolean dropStudentFromCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);
        if (student != null && course != null) {
            return student.dropCourse(course);
        }
        return false;
    }

    // Display a student's registered courses
    public void displayStudentCourses(String studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            System.out.println("Courses registered by " + student.name + ":");
            student.displayRegisteredCourses();
        } else {
            System.out.println("Student not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Add some sample courses
        system.addCourse(new Course("CS101", "Intro to Computer Science", "Basics of programming and algorithms", 30, "Mon, Wed, Fri 10:00 - 11:00 AM"));
        system.addCourse(new Course("MATH101", "Calculus I", "Introduction to calculus concepts", 40, "Tue, Thu 12:00 - 1:30 PM"));
        system.addCourse(new Course("PHY101", "Physics I", "Fundamentals of mechanics", 35, "Mon, Wed, Fri 2:00 - 3:30 PM"));

        // Add some sample students
        system.addStudent(new Student("S001", "Alice"));
        system.addStudent(new Student("S002", "Bob"));

        // Main menu
        while (true) {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. List Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch(choice) {
                case 1:
                    system.listCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String regStudentId = scanner.nextLine();
                    System.out.print("Enter Course Code to Register: ");
                    String regCourseCode = scanner.nextLine();
                    if (system.registerStudentForCourse(regStudentId, regCourseCode)) {
                        System.out.println("Registration successful.");
                    } else {
                        System.out.println("Registration failed. Course may be full or already registered.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    String dropStudentId = scanner.nextLine();
                    System.out.print("Enter Course Code to Drop: ");
                    String dropCourseCode = scanner.nextLine();
                    if (system.dropStudentFromCourse(dropStudentId, dropCourseCode)) {
                        System.out.println("Course dropped successfully.");
                    } else {
                        System.out.println("Drop failed. You may not be registered for this course.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    String viewStudentId = scanner.nextLine();
                    system.displayStudentCourses(viewStudentId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

