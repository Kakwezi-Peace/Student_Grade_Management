

import grade.manager.StudentManager;
import grade.manager.GradeManager;
import grade.model.Student;
import grade.model.Grade;
import grade.service.*;
import grade.exception.*;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager studentManager = new StudentManager();
    private static final GradeManager gradeManager = new GradeManager();

    // Lab 2 services
    private static final ReportGenerator reportGenerator = new ReportGenerator();
    private static final FileExporter fileExporter = new FileExporter();
    private static final GPACalculator gpaCalculator = new GPACalculator();
    private static final StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
    private static final CSVParser csvParser = new CSVParser();
    private static final BulkImportService bulkImportService = new BulkImportService(csvParser, studentManager);
    private static final SearchService searchService = new SearchService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getChoice();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> recordGrade();
                case 4 -> viewGradeReport();
                case 5 -> exportGradeReport();       // NEW
                case 6 -> calculateStudentGPA();     // NEW
                case 7 -> bulkImportGrades();        // NEW
                case 8 -> viewClassStatistics();     // NEW
                case 9 -> searchStudents();          // NEW
                case 10 -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Thank you for using Student Grade Management System!");
    }

    private static void printMenu() {
        System.out.println("\nSTUDENT GRADE MANAGEMENT - MAIN MENU");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Record Grade");
        System.out.println("4. View Grade Report");
        System.out.println("5. Export Grade Report [NEW]");
        System.out.println("6. Calculate Student GPA [NEW]");
        System.out.println("7. Bulk Import Grades [NEW]");
        System.out.println("8. View Class Statistics [NEW]");
        System.out.println("9. Search Students [NEW]");
        System.out.println("10. Exit");
        System.out.print("Enter choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // === Existing Lab 1 methods (simplified placeholders) ===
    private static void addStudent() {
        System.out.println("Add Student feature (Lab 1)");
        // Implementation from Lab 1
    }

    private static void viewStudents() {
        studentManager.viewAllStudents();
    }

    private static void recordGrade() {
        System.out.println("Record Grade feature (Lab 1)");
        // Implementation from Lab 1
    }

    private static void viewGradeReport() {
        System.out.println("View Grade Report feature (Lab 1)");
        // Implementation from Lab 1
    }

    // === Lab 2 NEW Features ===

    private static void exportGradeReport() {
        System.out.println("EXPORT GRADE REPORT");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = studentManager.findStudent(id);
        if (student == null) {
            System.out.println("ERROR: Student not found.");
            return;
        }
        List<Grade> grades = gradeManager.getGradesByStudent(id);

        System.out.println("Export options:\n1. Summary Report\n2. Detailed Report\n3. Both");
        int option = getChoice();
        String content = "";
        if (option == 1) content = reportGenerator.generateSummaryReport(student, grades);
        else if (option == 2) content = reportGenerator.generateDetailedReport(student, grades);
        else if (option == 3) {
            content = reportGenerator.generateSummaryReport(student, grades) + "\n\n"
                    + reportGenerator.generateDetailedReport(student, grades);
        }

        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine();
        try {
            fileExporter.exportToFile(filename, content);
            System.out.println("Report exported successfully!");
        } catch (ExportFailedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void calculateStudentGPA() {
        System.out.println("CALCULATE STUDENT GPA");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = studentManager.findStudent(id);
        if (student == null) {
            System.out.println("ERROR: Student not found.");
            return;
        }
        List<Grade> grades = gradeManager.getGradesByStudent(id);
        double gpa = gpaCalculator.calculateGPA(grades);
        System.out.printf("Cumulative GPA: %.2f / 4.0%n", gpa);
    }

    private static void bulkImportGrades() {
        System.out.println("BULK IMPORT GRADES");
        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine();
        bulkImportService.importGrades(filename);
    }

    private static void viewClassStatistics() {
        System.out.println("CLASS STATISTICS");
        List<Grade> allGrades = gradeManager.getAllGrades();
        System.out.printf("Mean: %.2f%%%n", statisticsCalculator.calculateMean(allGrades));
        System.out.printf("Median: %.2f%%%n", statisticsCalculator.calculateMedian(allGrades));
        System.out.printf("Std Dev: %.2f%%%n", statisticsCalculator.calculateStandardDeviation(allGrades));
    }

    private static void searchStudents() {
        System.out.println("SEARCH STUDENTS");
        System.out.println("1. By Student ID\n2. By Name");
        int option = getChoice();
        if (option == 1) {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine();
            Student s = searchService.searchById(studentManager.getAllStudents(), id);
            if (s != null) System.out.println("Found: " + s.getName());
            else System.out.println("No student found.");
        } else if (option == 2) {
            System.out.print("Enter name (partial): ");
            String name = scanner.nextLine();
            List<Student> results = searchService.searchByName(studentManager.getAllStudents(), name);
            results.forEach(s -> System.out.println(s.getStudentId() + " - " + s.getName()));
        }
    }
}
