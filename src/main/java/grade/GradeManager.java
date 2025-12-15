package grade;


public class GradeManager {
    private final Grade[] grades = new Grade[200];
    private int gradeCount = 0;

    public boolean addGrade(Grade grade) {
        if (gradeCount >= grades.length) return false;
        grades[gradeCount++] = grade;
        return true;
    }

    public void viewGradesByStudent(String studentId) {
        // Reverse chronological (newest first): scan backward
        System.out.println("GRD ID | DATE | SUBJECT | TYPE | GRADE");
        for (int i = gradeCount - 1; i >= 0; i--) {
            Grade g = grades[i];
            if (g != null && g.getStudentId().equals(studentId)) {
                g.displayGradeDetails();
            }
        }
    }

    public double calculateCoreAverage(String studentId) {
        double sum = 0; int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public double calculateElectiveAverage(String studentId) {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public double calculateOverallAverage(String studentId) {
        double sum = 0; int count = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
            if (g != null && g.getStudentId().equals(studentId)) {
                sum += g.getGrade(); count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public int getGradeCount() { return gradeCount; }

    public double[] getGradesForStudent(String studentId) {
        double[] buffer = new double[gradeCount];
        int idx = 0;
        for (int i = 0; i < gradeCount; i++) {
            Grade g = grades[i];
            if (g != null && g.getStudentId().equals(studentId)) {
                buffer[idx++] = g.getGrade();
            }
        }
        double[] result = new double[idx];
        System.arraycopy(buffer, 0, result, 0, idx);
        return result;
    }
}
