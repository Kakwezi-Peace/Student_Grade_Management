package model;

import grade.GradeManager;

public abstract class Student {
    private final String studentId;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String status; // e.g., "Active"
    private static int studentCounter = 0;

    // Managers are injected by composition in higher-level classes; not stored here.
    public Student(String name, int age, String email, String phone) {
        this.studentId = generateId();
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.status = "Active";
    }

    private String generateId() {
        studentCounter++;
        return String.format("STU%03d", studentCounter);
    }
// abstract methods
    public abstract void displayStudentDetails();
    public abstract String getStudentType();
    public abstract double getPassingGrade();

    // Will be called by StudentManager using shared GradeManager
    public double calculateAverageGrade(GradeManager gradeManager) {
        double[] grades = gradeManager.getGradesForStudent(studentId);
        if (grades.length == 0) return 0.0;
        double sum = 0;
        for (double g : grades) sum += g;
        return sum / grades.length;
    }

    public boolean isPassing(GradeManager gradeManager) {
        return calculateAverageGrade(gradeManager) >= getPassingGrade();
    }

    // Getters/Setters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getStatus() { return status; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setStatus(String status) { this.status = status; }

    public static int getStudentCounter() { return studentCounter; }
    public static void resetCounter() { studentCounter = 0; }
}
