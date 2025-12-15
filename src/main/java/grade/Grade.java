package grade;

import model.Subject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Grade {
    private static int gradeCounter = 0;

    private final String gradeId;
    private final String studentId;
    private final Subject subject;   // ✅ now this is model.Subject
    private final double grade;
    private final String date;

    public Grade(String studentId, Subject subject, double grade) {
        this.gradeId = generateId();
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private String generateId() {
        gradeCounter++;
        return String.format("GRD%03d", gradeCounter);
    }

    public String getLetterGrade() {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public void displayGradeDetails() {
        System.out.printf("%s | %s | %s | %s | %.1f%%%n",
                gradeId, date, subject.getSubjectName(), subject.getSubjectType(), grade);
    }

    public String getGradeId() { return gradeId; }
    public String getStudentId() { return studentId; }
    public Subject getSubject() { return subject; }
    public double getGrade() { return grade; }
    public String getDate() { return date; }

    public static int getGradeCounter() { return gradeCounter; }
    public static void resetCounter() { gradeCounter = 0; }
}
