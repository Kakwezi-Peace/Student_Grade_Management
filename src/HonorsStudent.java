// HonorsStudent.java
public class HonorsStudent extends Student {
    private static final double PASSING_GRADE = 60.0;
    private boolean honorsEligible = false; // derived from average

    public HonorsStudent(String name, int age, String email, String phone) {
        super(name, age, email, phone);
    }
  // Honors eligibility checking method verifying if average is >=85
    public boolean checkHonorsEligibility(GradeManager gradeManager) {
        double avg = calculateAverageGrade(gradeManager);
        honorsEligible = avg >= 85.0;
        return honorsEligible;
    }

    public boolean isHonorsEligible() {
        return honorsEligible;
    }

    @Override
    public void displayStudentDetails() {
        System.out.println("Student ID: " + getStudentId());
        System.out.println("Name: " + getName());
        System.out.println("Type: Honors");
        System.out.println("Age: " + getAge());
        System.out.println("Email: " + getEmail());
        System.out.println("Passing Grade: " + PASSING_GRADE + "%");
        System.out.println("Honors Eligible: " + (honorsEligible ? "Yes" : "No"));
        System.out.println("Status: " + getStatus());
    }

    @Override
    public String getStudentType() {
        return "Honors";
    }

    @Override
    public double getPassingGrade() {
        return PASSING_GRADE;
    }
}