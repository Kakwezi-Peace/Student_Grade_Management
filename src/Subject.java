// Subject.java
public abstract class Subject {
    // variables
    private String subjectName;
    private String subjectCode;


     // the constructor called from the parent class
    public Subject(String subjectName, String subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }
  // getters
    public String getSubjectName()
    { return subjectName; }
    public String getSubjectCode()
    { return subjectCode; }

    // setters
    public void setSubjectName(String subjectName)
    { this.subjectName = subjectName; }
    public void setSubjectCode(String subjectCode)
    { this.subjectCode = subjectCode; }

    // abstract methods from super class
    public abstract void displaySubjectDetails();
    public abstract String getSubjectType();
}