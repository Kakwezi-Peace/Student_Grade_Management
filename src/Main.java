// Main.java
import grade.Grade;
import grade.GradeManager;
import manager.StudentManager;
import student.HonorsStudent;
import student.RegularStudent;
import student.Student;
import subject.CoreSubject;
import subject.ElectiveSubject;
import subject.Subject;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Predefined subjects
    private static final Subject[] coreSubjects = {
            new CoreSubject("Mathematics", "MAT101"),
            new CoreSubject("English", "ENG101"),
            new CoreSubject("Science", "SCI101")
    };
    private static final Subject[] electiveSubjects = {
            new ElectiveSubject("Music", "MUS201"),
            new ElectiveSubject("Art", "ART201"),
            new ElectiveSubject("Physical Education", "PE201")
    };

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        GradeManager gradeManager = new GradeManager();

        seedInitialStudents(studentManager);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addStudentFlow(studentManager);
                case 2 -> studentManager.viewAllStudents(gradeManager);
                case 3 -> recordGradeFlow(studentManager, gradeManager);
                case 4 -> viewGradeReportFlow(studentManager, gradeManager);
                case 5 -> {
                    System.out.println("Thank you for using Student Grade Management System! Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-5.");
            }
            if (running) {
                System.out.println("Press Enter to continue ...");
                scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        System.out.println("==== STUDENT GRADE MANAGEMENT ====");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Record Grade");
        System.out.println("4. View Grade Report");
        System.out.println("5. Exit");
    }

    private static void addStudentFlow(StudentManager studentManager) {
        System.out.println("ADD STUDENT");
        String name = readLine("Enter student name: ");
        int age = readInt("Enter student age: ");
        String email = readLine("Enter student email: ");
        String phone = readLine("Enter student phone: ");

        System.out.println("Student type:");
        System.out.println("1. Regular Student (Passing grade: 50%)");
        System.out.println("2. Honors Student (Passing grade: 60%, honors recognition)");
        int type = readInt("Select type (1-2): ");

        Student student;
        if (type == 1) {
            student = new Student(name, age, email, phone) {
                @Override
                public void displayStudentDetails() {

                }

                @Override
                public String getStudentType() {
                    return "";
                }

                @Override
                public double getPassingGrade() {
                    return 0;
                }
            };
        } else if (type == 2) {
            student = new HonorsStudent(name, age, email, phone);
        } else {
            System.out.println("Invalid type selected.");
            return;
        }

        if (studentManager.addStudent((RegularStudent) student)) {
            System.out.println("Student added successfully!");
            student.displayStudentDetails();
        } else {
            System.out.println("Failed to add student: capacity reached.");
        }
    }

    private static void recordGradeFlow(StudentManager studentManager, GradeManager gradeManager) {
        System.out.println("RECORD GRADE");
        String studentId = readLine("Enter Student ID: ");
        Student student = studentManager.findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double currentAvg = gradeManager.calculateOverallAverage(studentId);
        System.out.println("Student: " + student.getStudentId() + " - " + student.getName());
        System.out.println("Type: " + student.getStudentType() + " Student");
        System.out.println("Current Average: " + String.format("%.1f", currentAvg) + "%");

        System.out.println("Subject type:");
        System.out.println("1. Core Subject (Mathematics, English, Science)");
        System.out.println("2. Elective Subject (Music, Art, Physical Education)");
        int stype = readInt("Select type (1-2): ");

        Subject subject;
        if (stype == 1) {
            listSubjects(coreSubjects);
            int sel = readInt("Select subject (1-3): ");
            if (sel < 1 || sel > coreSubjects.length) { System.out.println("Invalid subject."); return; }
            subject = coreSubjects[sel - 1];
        } else if (stype == 2) {
            listSubjects(electiveSubjects);
            int sel = readInt("Select subject (1-3): ");
            if (sel < 1 || sel > electiveSubjects.length) { System.out.println("Invalid subject."); return; }
            subject = electiveSubjects[sel - 1];
        } else {
            System.out.println("Invalid subject type.");
            return;
        }

        double gradeValue = readDouble("Enter grade (0-100): ");
        if (!gradeManager.validateGrade(gradeValue)) {
            System.out.println("Invalid grade. Must be between 0 and 100.");
            return;
        }

        Grade grade = new Grade(studentId, subject, gradeValue);
        System.out.println("GRADE CONFIRMATION");
        grade.displayGradeDetails();
        String confirm = readLine("Confirm grade? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            boolean ok = gradeManager.recordGrade(grade);
            System.out.println(ok ? "Grade recorded successfully!" : "Failed to record grade.");
        } else {
            System.out.println("Grade entry cancelled.");
        }
    }

    private static void viewGradeReportFlow(StudentManager studentManager, GradeManager gradeManager) {
        System.out.println("VIEW GRADE REPORT");
        String studentId = readLine("Enter Student ID: ");
        Student student = studentManager.findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student: " + student.getStudentId() + " - " + student.getName());
        double overall = gradeManager.calculateOverallAverage(studentId);
        if (overall == 0.0) {
            System.out.println("Passing Grade: " + student.getPassingGrade() + "%");
            System.out.println("Type: " + student.getStudentType() + " Student");
            System.out.println("No grades recorded for this student.");
            return;
        }

        boolean passing = student.isPassing(gradeManager);
        System.out.println("Current Average: " + String.format("%.1f", overall) + "%");
        System.out.println("Type: " + student.getStudentType() + " Student");
        System.out.println("Status: " + (passing ? "PASSING" : "FAILING"));

        gradeManager.viewGradesByStudent(studentId);

        double coreAvg = gradeManager.calculateCoreAverage(studentId);
        double elecAvg = gradeManager.calculateElectiveAverage(studentId);

        System.out.println("Core Subjects Average: " + String.format("%.1f", coreAvg) + "%");
        System.out.println("Elective Subjects Average: " + String.format("%.1f", elecAvg) + "%");
        System.out.println("Overall Average: " + String.format("%.1f", overall) + "%");

        if (student instanceof HonorsStudent honors) {
            boolean eligible = honors.checkHonorsEligibility(gradeManager);
            System.out.println("Honors Eligible: " + (eligible ? "Yes" : "No"));
        }
    }
    // Helpers
    private static void seedInitialStudents(StudentManager m) {
        m.addStudent(new RegularStudent("Mugabekazi Enid", 16, "enid@school.org", "+250-111-111"));
        m.addStudent(new HonorsStudent("Bwiza Eric", 17, "bwiza@school.org", "+250-222-222"));
        m.addStudent(new RegularStudent("Munny Vin", 16, "munny@school.org", "+250-333-333"));
        m.addStudent(new HonorsStudent("peace kakwezi", 18, "peace@school.org", "+250-444-444"));
        m.addStudent(new RegularStudent("Divine Bugingo", 54, "bugingo@school.org", "+250-555-555"));
    }

    private static void listSubjects(Subject[] subjects) {
        System.out.println("Available " + subjects[0].getSubjectType() + " Subjects:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.println((i + 1) + ". " + subjects[i].getSubjectName());
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (e.g., 75 or 92.5).");
            }
        }
    }
}