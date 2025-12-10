package model;

public class ElectiveSubject extends Subject {
    private final boolean mandatory = false;

    public ElectiveSubject(String name, String code) {
        super(name, code);
    }
// method to override
    @Override
    public void displaySubjectDetails() {
        System.out.printf("Elective Subject: %s (%s), mandatory: %b%n", getSubjectName(), getSubjectCode(), mandatory);
    }

    @Override
    public String getSubjectType() {
        return "Elective";
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
