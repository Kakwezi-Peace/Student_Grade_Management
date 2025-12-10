package model;


import grade.GradeManager;

public class HonorsStudent extends Student {
    private final double passingGrade = 60.0;

    public HonorsStudent(String name, int age, String email, String phone) {
        super(name, age, email, phone);
    }

    @Override
    public void displayStudentDetails() {
        System.out.println("Type: Honors");
        System.out.println("Passing Grade: " + passingGrade + "%");
        // Honors flag will be computed via average at display time
    }
// methods to override
    @Override
    public String getStudentType() {
        return "Honors";
    }

    @Override
    public double getPassingGrade() {
        return passingGrade;
    }

    public boolean checkHonorsEligibility(GradeManager gradeManager) {
        return calculateAverageGrade(gradeManager) >= 85.0;
    }

    public double calculateAverageGrade(GradeManager gradeManager) {
        return 0;
    }
}
