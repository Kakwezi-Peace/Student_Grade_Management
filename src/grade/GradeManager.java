package grade;

// GradeManager.java
public class GradeManager implements Gradable {
    private final Grade[] grades = new Grade[200];
    private int gradeCount = 0;

    @Override
    public boolean validateGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    @Override
    public boolean recordGrade(Grade grade) {
        if (gradeCount >= grades.length) return false;
        grades[gradeCount++] = grade;
        return true;
    }

    public int getGradeCount() { return gradeCount; }

    public void addGrade(Grade grade) {
        recordGrade(grade);
    }

    public void viewGradesByStudent(String studentId) {
        // reverse chronological: newest first (array order is insertion)
        boolean foundAny = false;
        System.out.println("GRADE HISTORY");
        int total = 0;
        for (int i = gradeCount - 1; i >= 0; i--) {
            Grade g = grades[i];
            if (g.getStudentId().equalsIgnoreCase(studentId)) {
                foundAny = true;
                total++;
                System.out.printf("%s | %s | %-20s | %-8s | %s%%%n",
                        g.getGradeId(),
                        g.getDate(),
                        g.getSubject().getSubjectName(),
                        g.getSubject().getSubjectType(),
                        String.format("%.1f", g.getGrade()));
            }
        }
        if (!foundAny) {
            System.out.println("No grades recorded for this student.");
        } else {
            System.out.println("Total Grades: " + total);
        }
    }

    public double calculateCoreAverage(String studentId) {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
            if (g.getStudentId().equalsIgnoreCase(studentId)
                    && "Core".equalsIgnoreCase(g.getSubject().getSubjectType())) {
                sum += g.getGrade(); count++;
            }
        }
        return count == 0 ? 0.0 : (sum / count);
    }

    public double calculateElectiveAverage(String studentId) {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
            if (g.getStudentId().equalsIgnoreCase(studentId)
                    && "Elective".equalsIgnoreCase(g.getSubject().getSubjectType())) {
                sum += g.getGrade(); count++;
            }
        }
        return count == 0 ? 0.0 : (sum / count);
    }

    public double calculateOverallAverage(String studentId) {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
            if (g.getStudentId().equalsIgnoreCase(studentId)) {
                sum += g.getGrade(); count++;
            }
        }
        return count == 0 ? 0.0 : (sum / count);
    }
}
