// Grade.java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Grade {
    private static int gradeCounter = 1;

    private final String gradeId;
    private final String studentId;
    private final Subject subject;
    private final double grade;
    private final String date; // format: dd-MM-yyyy

    public Grade(String studentId, Subject subject, double grade) {
        this.gradeId = "GRD" + String.format("%03d", gradeCounter++);
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getGradeId() { return gradeId; }
    public String getStudentId() { return studentId; }
    public Subject getSubject() { return subject; }
    public double getGrade() { return grade; }
    public String getDate() { return date; }

    public void displayGradeDetails() {
        System.out.println("Grade ID: " + gradeId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Subject: " + subject.getSubjectName() + " (" + subject.getSubjectType() + ")");
        System.out.println("Grade: " + String.format("%.1f", grade) + "%");
        System.out.println("Date: " + date);
    }

    public String getLetterGrade() {
        double g = this.grade;
        if (g >= 90) return "A";
        if (g >= 80) return "B";
        if (g >= 70) return "C";
        if (g >= 60) return "D";
        return "F";
    }
}