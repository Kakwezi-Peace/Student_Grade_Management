package model;

public abstract class Subject {
    private String subjectName;
    private String subjectCode;

    public Subject(String subjectName, String subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public abstract void displaySubjectDetails();
    public abstract String getSubjectType();

    public String getSubjectName() { return subjectName; }
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
}
