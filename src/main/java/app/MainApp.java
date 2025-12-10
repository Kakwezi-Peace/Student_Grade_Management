package app;

import grade.GradeManager;
import grade.Grade;
import model.HonorsStudent;
import model.RegularStudent;
import model.Subject;
import model.CoreSubject;
import model.ElectiveSubject;
import model.Student;
import service.*;
import student.StudentManager;

import java.io.File;
import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        GradeManager gradeManager = new GradeManager();

        // Seed initial 5 students (3 Regular, 2 Honors)
        seedInitialStudents(studentManager);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudentFlow(scanner, studentManager);
                case "2" -> studentManager.viewAllStudents( gradeManager);
                case "3" -> recordGradeFlow(scanner, studentManager, gradeManager);
                case "4" -> viewGradeReportFlow(scanner, studentManager, gradeManager);
                case "5" -> searchStudentsFlow(scanner, studentManager, gradeManager);
                case "6" -> viewClassStatistics(studentManager, gradeManager);
                case "7" -> bulkImportFlow(scanner, studentManager, gradeManager);

                case "8" -> {
                    System.out.println("Thank you for using Student Grade Management System!");
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-5.");
            }
            if (running) {
                System.out.println("Press Enter to continue ...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }




    /// //////////////////////////////
    private static void printMenu() {
        System.out.println("STUDENT GRADE MANAGEMENT - MAIN MENU");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Record Grade");
        System.out.println("4. View Grade Report");
        System.out.println("5. Search Students");
        System.out.println("6. View Class Statistics");
        System.out.println("7. Bulk Import Grades");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private static void seedInitialStudents(StudentManager sm) {
        sm.addStudent(new RegularStudent("Alice Johnson", 16, "alice@school.edu", "+250-111"));
        sm.addStudent(new HonorsStudent("Bob Smith", 17, "bob@school.edu", "+250-112"));
        sm.addStudent(new RegularStudent("Carol Martinez", 16, "carol@school.edu", "+250-113"));
        sm.addStudent(new HonorsStudent("David Chen", 17, "david@school.edu", "+250-114"));
        sm.addStudent(new RegularStudent("Emma Wilson", 16, "emma@school.edu", "+250-115"));
    }

    private static void addStudentFlow(Scanner sc, StudentManager sm) {
        System.out.println("ADD STUDENT");
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter student age: ");
        int age = parseIntSafe(sc.nextLine().trim(), 16);
        System.out.print("Enter student email: ");
        String email = sc.nextLine().trim();
        System.out.print("Enter student phone: ");
        String phone = sc.nextLine().trim();
        System.out.println("Student type:");
        System.out.println("1. Regular Student (Passing grade: 50%)");
        System.out.println("2. Honors Student (Passing grade: 60%, honors recognition)");
        System.out.print("Select type (1-2): ");
        String type = sc.nextLine().trim();

        boolean ok;
        if ("2".equals(type)) {
            HonorsStudent s = new HonorsStudent(name, age, email, phone);
            ok = sm.addStudent(s);
            if (ok) {
                System.out.println("Student added successfully!");
                System.out.println("Student ID: " + s.getStudentId());
                System.out.println("Name: " + s.getName());
                System.out.println("Type: Honors");
                System.out.println("Age: " + s.getAge());
                System.out.println("Email: " + s.getEmail());
                System.out.println("Passing Grade: 60%");
                System.out.println("Honors Eligible: (depends on grades)");
                System.out.println("Status: Active");
            }
        } else {
            RegularStudent s = new RegularStudent(name, age, email, phone);
            ok = sm.addStudent(s);
            if (ok) {
                System.out.println("Student added successfully!");
                System.out.println("Student ID: " + s.getStudentId());
                System.out.println("Name: " + s.getName());
                System.out.println("Type: Regular");
                System.out.println("Age: " + s.getAge());
                System.out.println("Email: " + s.getEmail());
                System.out.println("Passing Grade: 50%");
                System.out.println("Status: Active");
            }
        }
        if (!ok) System.out.println("Could not add student (capacity reached).");
    }
// method section
    private static void recordGradeFlow(Scanner sc, StudentManager sm, GradeManager gm) {
        System.out.println("RECORD GRADE");
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine().trim();
        Student s = sm.findStudent(studentId);
        if (s == null) {
            System.out.println("ERROR: Student not found.");
            return;
        }
        double currentAvg;
        currentAvg = s.calculateAverageGrade(gm);
        System.out.printf("Student: %s - %s%nType: %s%nCurrent Average: %.1f%%%n",
                s.getStudentId(), s.getName(), s.getStudentType(), currentAvg);

        System.out.println("Subject type:");
        System.out.println("1. Core Subject (Mathematics, English, Science)");
        System.out.println("2. Elective Subject (Music, Art, Physical Education)");
        System.out.print("Select type (1-2): ");
        String typeChoice = sc.nextLine().trim();

        Subject subject;
        if ("1".equals(typeChoice)) {
            System.out.println("Available Core Subjects:\n1. Mathematics\n2. English\n3. Science");
            System.out.print("Select subject (1-3): ");
            subject = switch (sc.nextLine().trim()) {
                case "1" -> new CoreSubject("Mathematics", "MAT");
                case "2" -> new CoreSubject("English", "ENG");
                case "3" -> new CoreSubject("Science", "SCI");
                default -> null;
            };
        } else if ("2".equals(typeChoice)) {
            System.out.println("Available Elective Subjects:\n1. Music\n2. Art\n3. Physical Education");
            System.out.print("Select subject (1-3): ");
            subject = switch (sc.nextLine().trim()) {
                case "1" -> new ElectiveSubject("Music", "MUS");
                case "2" -> new ElectiveSubject("Art", "ART");
                case "3" -> new ElectiveSubject("Physical Education", "PE");
                default -> null;
            };
        } else {
            subject = null;
        }

        if (subject == null) {
            System.out.println("Invalid subject selection.");
            return;
        }

        System.out.print("Enter grade (0-100): ");
        String gradeStr = sc.nextLine().trim();
        double gradeVal = parseDoubleSafe(gradeStr, -1);
        if (gradeVal < 0 || gradeVal > 100) {
            System.out.println("Invalid grade. Must be between 0 and 100.");
            return;
        }

        Grade grade = new Grade(s.getStudentId(), subject, gradeVal);
        System.out.println("GRADE CONFIRMATION");
        System.out.printf("Grade ID: %s%nStudent: %s - %s%nSubject: %s (%s)%nGrade: %.1f%%%nDate: %s%n",
                grade.getGradeId(), s.getStudentId(), s.getName(),
                subject.getSubjectName(), subject.getSubjectType(),
                grade.getGrade(), grade.getDate());
        System.out.print("Confirm grade? (Y/N): ");
        if ("Y".equalsIgnoreCase(sc.nextLine().trim())) {
            boolean ok = gm.addGrade(grade);
            System.out.println(ok ? "Grade recorded successfully!" : "Failed to record grade (capacity reached).");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private static void viewGradeReportFlow(Scanner sc, StudentManager sm, GradeManager gm) {
        System.out.println("VIEW GRADE REPORT");
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine().trim();
        Student s = sm.findStudent(studentId);
        if (s == null) {
            System.out.println("ERROR: Student not found.");
            return;
        }
        System.out.printf("Student: %s - %s%n", s.getStudentId(), s.getName());
        System.out.printf("Passing Grade: %.0f%%%n", s.getPassingGrade());
        System.out.printf("Type: %s Student%n", s.getStudentType());

        double overall = gm.calculateOverallAverage(studentId);
        if (gm.getGradesForStudent(studentId).length == 0) {
            System.out.println("No grades recorded for this student.");
            return;
        }
        System.out.printf("Current Average: %.1f%%%n", overall);
        System.out.println("GRADE HISTORY");
        gm.viewGradesByStudent(studentId);

        double coreAvg = gm.calculateCoreAverage(studentId);
        double electiveAvg = gm.calculateElectiveAverage(studentId);
        System.out.printf("Core Subjects Average: %.1f%%%n", coreAvg);
        System.out.printf("Elective Subjects Average: %.1f%%%n", electiveAvg);
        System.out.printf("Overall Average: %.1f%%%n", overall);
        System.out.println("Performance Summary:");
        System.out.println(s.isPassing(gm) ? "Passing requirements met" : "Below passing requirements");
    }
    /// //////////////////////////////


// Handles US-6: Search Students feature
    private static void searchStudentsFlow(Scanner sc, StudentManager sm, GradeManager gm) {
        // Create a SearchService instance to perform different types of searches
        SearchService searchService = new SearchService();

        while (true) {
            // Display search options
            System.out.println("SEARCH STUDENTS");
            System.out.println("1. By Student ID");
            System.out.println("2. By Name (partial match)");
            System.out.println("3. By Grade Range");
            System.out.println("4. By Student Type");
            System.out.print("Select option (1-4): ");
            String choice = sc.nextLine().trim();

            List<Student> results = new ArrayList<>();

            // Perform the selected search
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter Student ID: ");
                    String id = sc.nextLine().trim();
                    results = searchService.searchById(sm, id);
                }
                case "2" -> {
                    System.out.print("Enter name (partial or full): ");
                    String name = sc.nextLine().trim();
                    results = searchService.searchByName(sm, name);
                }
                case "3" -> {
                    System.out.print("Enter minimum grade: ");
                    double min = parseDoubleSafe(sc.nextLine().trim(), 0);
                    System.out.print("Enter maximum grade: ");
                    double max = parseDoubleSafe(sc.nextLine().trim(), 100);
                    results = searchService.searchByGradeRange(sm, gm, min, max);
                }
                case "4" -> {
                    System.out.print("Enter student type (Regular/Honors): ");
                    String type = sc.nextLine().trim();
                    results = searchService.searchByType(sm, type);
                }
                default -> {
                    System.out.println("Invalid option.");
                    return;
                }
            }

            // Display results or message if none found
            if (results.isEmpty()) {
                System.out.println("No matching students found.");
            } else {
                System.out.println("SEARCH RESULTS (" + results.size() + " found)");
                System.out.println("STU ID | NAME | TYPE | AVG");
                for (Student s : results) {
                    double avg = s.calculateAverageGrade(gm);
                    System.out.printf("%s | %s | %s | %.1f%%%n",
                            s.getStudentId(), s.getName(), s.getStudentType(), avg);
                }

                // Provide actions on search results
                System.out.println("Actions:");
                System.out.println("1. View full details for a student");
                System.out.println("2. Export search results");
                System.out.println("3. New search");
                System.out.println("4. Return to main menu");
                System.out.print("Enter choice: ");
                String action = sc.nextLine().trim();

                switch (action) {
                    case "1" -> {
                        // View full details of a selected student
                        System.out.print("Enter Student ID to view: ");
                        String id = sc.nextLine().trim();
                        Student s = sm.findStudent(id);
                        if (s != null) {
                            System.out.printf("Student: %s - %s%n", s.getStudentId(), s.getName());
                            System.out.printf("Type: %s%n", s.getStudentType());
                            System.out.printf("Email: %s%n", s.getEmail());
                            System.out.printf("Phone: %s%n", s.getPhone());
                            System.out.printf("Average Grade: %.1f%%%n", s.calculateAverageGrade(gm));
                            System.out.println("Status: " + (s.isPassing(gm) ? "PASSING" : "FAILING"));
                        } else {
                            System.out.println("Student not found.");
                        }
                    }
                    case "2" -> {
                        // Export search results to a text file
                        System.out.print("Enter filename (without extension): ");
                        String filename = sc.nextLine().trim();
                        StringBuilder content = new StringBuilder();
                        content.append("SEARCH RESULTS\n");
                        content.append("STU ID | NAME | TYPE | AVG\n");
                        for (Student s : results) {
                            double avg = s.calculateAverageGrade(gm);
                            content.append(String.format("%s | %s | %s | %.1f%%%n",
                                    s.getStudentId(), s.getName(), s.getStudentType(), avg));
                        }

                        // Use FileExporter to save the file
                        FileExporter exporter = new FileExporter(new File("src/main/resources/reports"));
                        try {
                            File file = exporter.exportText(filename, content.toString());
                            System.out.println("Search results exported successfully!");
                            System.out.println("File: " + file.getName());
                            System.out.println("Location: " + file.getAbsolutePath());
                        } catch (exception.ReportExportException e) {
                            System.out.println("Export failed: " + e.getMessage());
                        }
                    }
                    case "3" -> searchStudentsFlow(sc, sm, gm); // restart search
                    case "4" -> { return; } // exit to main menu
                    default -> System.out.println("Invalid action.");
                }
            }
        }
    }
    /// ////////////

// US-5: View Class Statistics
    private static void viewClassStatistics(StudentManager sm, GradeManager gm) {
        StatisticsCalculator stats = new StatisticsCalculator();
        double[] allGrades = stats.getAllGrades(sm, gm);

        System.out.println("CLASS STATISTICS");
        System.out.println("Total Students: " + sm.getStudentCount());
        System.out.println("Total Grades Recorded: " + allGrades.length);

        // Grade distribution
        Map<String, Integer> dist = stats.gradeDistribution(allGrades);
        System.out.println("GRADE DISTRIBUTION");
        for (Map.Entry<String, Integer> e : dist.entrySet()) {
            double pct = (e.getValue() * 100.0) / allGrades.length;
            System.out.printf("%s: %d grades (%.1f%%)%n", e.getKey(), e.getValue(), pct);
        }

        // Statistical measures
        System.out.println("\nSTATISTICAL ANALYSIS");
        System.out.printf("Mean (Average): %.1f%%%n", stats.mean(allGrades));
        System.out.printf("Median: %.1f%%%n", stats.median(allGrades));
        System.out.printf("Mode: %.1f%%%n", stats.mode(allGrades));
        System.out.printf("Standard Deviation: %.1f%%%n", stats.stdDev(allGrades));

        // Highest and lowest grades
        double max = Arrays.stream(allGrades).max().orElse(0);
        double min = Arrays.stream(allGrades).min().orElse(0);
        System.out.printf("Highest Grade: %.1f%%%n", max);
        System.out.printf("Lowest Grade: %.1f%%%n", min);

        // Subject performance (simplified)
        System.out.println("\nSUBJECT PERFORMANCE");
        System.out.printf("Core Subjects Average: %.1f%%%n", gm.calculateCoreAverage("ALL"));
        System.out.printf("Elective Subjects Average: %.1f%%%n", gm.calculateElectiveAverage("ALL"));

        // Student type comparison
        double[] regular = stats.getGradesByType(sm, gm, "Regular");
        double[] honors = stats.getGradesByType(sm, gm, "Honors");
        System.out.println("\nSTUDENT TYPE COMPARISON");
        System.out.printf("Regular Students: %.1f%% average (%d grades)%n", stats.mean(regular), regular.length);
        System.out.printf("Honors Students: %.1f%% average (%d grades)%n", stats.mean(honors), honors.length);
    }
    /// ///////////////
    private static void bulkImportFlow(Scanner sc, StudentManager sm, GradeManager gm) {
        System.out.println("BULK IMPORT GRADES");
        System.out.println("Place your CSV file in: src/main/resources/imports/");
        System.out.println("CSV Format Required:");
        System.out.println("StudentID,SubjectName,SubjectType,Grade");
        System.out.println("Example: STU001,Mathematics,Core,85");

        System.out.print("Enter filename (without extension): ");
        String filename = sc.nextLine().trim();

        BulkImportService importer = new BulkImportService(
                sm, gm,
                new SimpleCSVParser(),
                new File("src/main/resources/imports"),
                new File("src/main/resources/logs")
        );

        try {
            BulkImportService.ImportResult result = importer.importFile(filename);
            System.out.println("IMPORT SUMMARY");
            System.out.println("Total Rows: " + (result.successCount + result.failureCount));
            System.out.println("Successfully Imported: " + result.successCount);
            System.out.println("Failed: " + result.failureCount);
            System.out.println("Log File: " + result.logFilename);
            System.out.println("Location: src/main/resources/logs/");
        } catch (exception.InvalidFileFormatException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }



    /// ////////////////////////////
    private static int parseIntSafe(String s, int fallback) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static double parseDoubleSafe(String s, double fallback) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
