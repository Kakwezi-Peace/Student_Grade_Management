package model;

public class RegularStudent extends Student {
    private final double passingGrade = 50.0;

    public RegularStudent(String name, int age, String email, String phone) {
        super(name, age, email, phone);
    }

    @Override
    public void displayStudentDetails() {
        System.out.println("Type: Regular");
        System.out.println("Passing Grade: " + passingGrade + "%");
    }

    @Override
    public String getStudentType() {
        return "Regular";
    }

    @Override
    public double getPassingGrade() {
        return passingGrade;
    }
}
