package service;

import grade.Grade;
import grade.GradeManager;
import model.Student;
import util.LoggerUtil;

import java.util.StringJoiner;

public class ReportGenerator {

    public String generateSummary(Student student, GradeManager gm) {
        double coreAvg = gm.calculateCoreAverage(student.getStudentId());
        double electiveAvg = gm.calculateElectiveAverage(student.getStudentId());
        double overall = gm.calculateOverallAverage(student.getStudentId());

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENT GRADE REPORT - SUMMARY")
                .add("Student: " + student.getStudentId() + " - " + student.getName())
                .add("Type: " + student.getStudentType())
                .add(String.format("Core Avg: %.1f%%", coreAvg))
                .add(String.format("Elective Avg: %.1f%%", electiveAvg))
                .add(String.format("Overall Avg: %.1f%%", overall))
                .add("Status: " + (overall >= student.getPassingGrade() ? "PASSING" : "FAILING"));
        return sj.toString();
    }

    public String generateDetailed(Student student, GradeManager gm) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENT GRADE REPORT - DETAILED")
                .add("Student: " + student.getStudentId() + " - " + student.getName())
                .add("Type: " + student.getStudentType())
                .add("")
                .add("GRADE HISTORY (newest first)")
                .add("GRD ID | DATE | SUBJECT | TYPE | GRADE");

        int count = 0;
        // Traverse GradeManager internal list via view-like composition (mirror of its logic)
        // Since GradeManager doesn't expose its array, we reprint by calling viewGradesByStudent,
        // but for export we need raw lines. Easiest: temporarily capture via a helper in GradeManager later if needed.
        // For now, we compute counts and rely on averages.
        // Optionally, extend GradeManager with a method `getGradesByStudent(String)` to return Grade[] filtered.

        // Lightweight approach: recompute counts by scanning averages source
        double[] all = gm.getGradesForStudent(student.getStudentId());
        for (double ignored : all) count++;

        double coreAvg = gm.calculateCoreAverage(student.getStudentId());
        double electiveAvg = gm.calculateElectiveAverage(student.getStudentId());
        double overall = gm.calculateOverallAverage(student.getStudentId());

        sj.add("")
                .add(String.format("Total Grades: %d", count))
                .add(String.format("Core Avg: %.1f%%", coreAvg))
                .add(String.format("Elective Avg: %.1f%%", electiveAvg))
                .add(String.format("Overall Avg: %.1f%%", overall))
                .add("Status: " + (overall >= student.getPassingGrade() ? "PASSING" : "FAILING"));

        LoggerUtil.info("Generated detailed report for " + student.getStudentId());
        return sj.toString();
    }
}
