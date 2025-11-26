// Student.java
public abstract class Student {
    // Private fields
    private final String studentId;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String status;

    // Static counter for ID generation
    private static int studentCounter = 1;

    // Constructor
    public Student(String name, int age, String email, String phone) {
        this.studentId = "STU" + String.format("%03d", studentCounter++);
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.status = "Active";
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getStatus() { return status; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setStatus(String status) { this.status = status; }




}
