package model;

public class CoreSubject extends Subject {
    private final boolean mandatory = true;

    public CoreSubject(String name, String code) {
        super(name, code);
    }
// overriding methods
    @Override
    public void displaySubjectDetails() {
        System.out.printf("Core Subject: %s (%s), mandatory: %b%n", getSubjectName(), getSubjectCode(), mandatory);
    }

    @Override
    public String getSubjectType() {
        return "Core";
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
