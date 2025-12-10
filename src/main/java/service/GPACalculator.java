package service;

import grade.Grade;
import grade.GradeManager;
import model.Student;

public class GPACalculator {

    // Map percentage to GPA points
    public double toGpa(double pct) {
        if (pct >= 93) return 4.0;
        if (pct >= 90) return 3.7;
        if (pct >= 87) return 3.3;
        if (pct >= 83) return 3.0;
        if (pct >= 80) return 2.7;
        if (pct >= 77) return 2.3;
        if (pct >= 73) return 2.0;
        if (pct >= 70) return 1.7;
        if (pct >= 67) return 1.3;
        if (pct >= 60) return 1.0;
        return 0.0;
    }
// to calculate the cumulative GPA
    public double cumulativeGpa(Student student, GradeManager gm) {
        double[] grades = gm.getGradesForStudent(student.getStudentId());
        if (grades.length == 0) return 0.0;
        double sum = 0.0;
        for (double g : grades) sum += toGpa(g);
        return sum / grades.length;
    }

    // Optional: letter grade mapping for display
    public String letterFor(double pct) {
        if (pct >= 93) return "A";
        if (pct >= 90) return "A-";
        if (pct >= 87) return "B+";
        if (pct >= 83) return "B";
        if (pct >= 80) return "B-";
        if (pct >= 77) return "C+";
        if (pct >= 73) return "C";
        if (pct >= 70) return "C-";
        if (pct >= 67) return "D+";
        if (pct >= 60) return "D";
        return "F";
    }
}
