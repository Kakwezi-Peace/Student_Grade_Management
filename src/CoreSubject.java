// CoreSubject.java
public class CoreSubject extends Subject {
    private final boolean mandatory = true;

    public CoreSubject(String name, String code) {
        super(name, code);
    }

    public boolean isMandatory() { return mandatory; }

    @Override
    public void displaySubjectDetails() {
        System.out.println(getSubjectName() + " (" + getSubjectCode() + ") - Core, mandatory: " + mandatory);
    }

    @Override
    public String getSubjectType() {
        return "Core";
    }
}
