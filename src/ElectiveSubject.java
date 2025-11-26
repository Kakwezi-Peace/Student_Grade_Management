// ElectiveSubject.java
public class ElectiveSubject extends Subject {
    private final boolean mandatory = false;

    public ElectiveSubject(String name, String code) {
        super(name, code);
    }

    public boolean isMandatory() { return mandatory; }

    @Override
    public void displaySubjectDetails() {
        System.out.println(getSubjectName() + " (" + getSubjectCode() + ") - Elective, mandatory: " + mandatory);
    }

    @Override
    public String getSubjectType() {
        return "Elective";
    }
}