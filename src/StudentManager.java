// StudentManager.java
public class StudentManager {
    private final Student[] students = new Student[50];
    private int studentCount = 0;



    // these are the methods

    // method to add students
    public boolean addStudent(Student student) {
        if (studentCount >= students.length) return false;
        students[studentCount++] = student;
        return true;
    }
  // method to find students
    public Student findStudent(String studentId) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equalsIgnoreCase(studentId)) {
                return students[i];
            }
        }
        return null;
    }
 // method to get student's account
    public int getStudentCount() {
        return studentCount;
    }
     // a method to view all students

    public void viewAllStudents(GradeManager gradeManager) {
        System.out.println("STUDENT LISTING");
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            double avg = gradeManager.calculateOverallAverage(s.getStudentId());
            boolean passing = avg >= s.getPassingGrade();

            System.out.printf("%s | %-15s | %-7s | Avg: %s%% | %s%n",
                    s.getStudentId(),
                    s.getName(),
                    s.getStudentType(),
                    String.format("%.1f", avg),
                    (passing ? "Passing" : "Failing"));

            // Honors flag
            if (s instanceof HonorsStudent) {
                HonorsStudent hs = (HonorsStudent) s;
                boolean eligible = hs.checkHonorsEligibility(gradeManager);
                System.out.println("  Honors Eligible: " + (eligible ? "Yes" : "No"));
            }
        }

        double classAvg = getAverageClassGrade(gradeManager);
        System.out.println("Total Students: " + studentCount);
        System.out.println("Average Class Grade: " + String.format("%.1f", classAvg) + "%");
    }
  // method to get average class grades
    public double getAverageClassGrade(GradeManager gradeManager) {
        if (studentCount == 0) return 0.0;
        double sum = 0.0;
        int counted = 0;
        for (int i = 0; i < studentCount; i++) {
            sum += gradeManager.calculateOverallAverage(students[i].getStudentId());
            counted++;
        }
        return counted == 0 ? 0.0 : (sum / counted);
    }
}
