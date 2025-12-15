package student;

import grade.GradeManager;
import model.Student;
import model.HonorsStudent;

public class StudentManager {
    private final Student[] students = new Student[50];
    private int studentCount = 0;

    // Add a student (Regular or Honors)
    public boolean addStudent(Student student) {
        if (studentCount >= students.length) return false;
        students[studentCount++] = student;
        return true;
    }

    // Find a student by ID
    public Student findStudent(String studentId) {
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            if (s != null && s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    // View all students with averages and status
    public void viewAllStudents(GradeManager gradeManager) {
        System.out.println("STU ID | NAME | TYPE | AVG | STATUS");
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            double avg = s.calculateAverageGrade(gradeManager);
            boolean passing = s.isPassing(gradeManager);
            String status = passing ? "Passing" : "Failing";
            System.out.printf("%s | %s | %s | %.1f%% | %s%n",
                    s.getStudentId(), s.getName(), s.getStudentType(), avg, status);

            if (s instanceof HonorsStudent honors) {
                System.out.printf("Enrolled Subjects: ? Passing | Grade: 60%% | Honors Eligible: %s%n",
                        honors.checkHonorsEligibility(gradeManager) ? "Yes" : "No");
            } else {
                System.out.println("Enrolled Subjects: ? Passing | Grade: 50%");
            }
        }
        System.out.printf("Total Students: %d%n", getStudentCount());
        System.out.printf("Average Class Grade: %.1f%%%n", getAverageClassGrade(gradeManager));
    }

    // Calculate average class grade
    public double getAverageClassGrade(GradeManager gradeManager) {
        if (studentCount == 0) return 0.0;
        double sum = 0;
        int withGrades = 0;
        for (int i = 0; i < studentCount; i++) {
            double avg = students[i].calculateAverageGrade(gradeManager);
            if (avg > 0 || gradeManager.getGradesForStudent(students[i].getStudentId()).length > 0) {
                sum += avg;
                withGrades++;
            }
        }
        return withGrades == 0 ? 0.0 : sum / withGrades;
    }

    // Get total student count
    public int getStudentCount() {
        return studentCount;
    }

    // Return a copy of all students
    public Student[] getAllStudents() {
        Student[] copy = new Student[studentCount];
        System.arraycopy(students, 0, copy, 0, studentCount);
        return copy;
    }
}
