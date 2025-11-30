package student;
// RegularStudent.java
public class RegularStudent extends Student {
    private final double passingGrade = 50.0;


    // this is the constructor inheriting traits from the super class using Super key word.
    public RegularStudent(String name, int age, String email, String phone) {
        super(name, age, email, phone);
    }

    // these are the methods that i overriden from the parent class
    @Override
    public void displayStudentDetails() {// will display student info
        System.out.println("ID: " + getStudentId());
        System.out.println("Name: " + getName());
        System.out.println("Type: Regular Student");
        System.out.println("Age: " + getAge());
        System.out.println("Email: " + getEmail());
        System.out.println("Passing Grade: " + passingGrade + "%");
        System.out.println("Status: " + getStatus());
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

