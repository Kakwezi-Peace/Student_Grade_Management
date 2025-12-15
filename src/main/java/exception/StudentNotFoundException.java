package exception;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String studentId) {
        super("Student with ID '" + studentId + "' not found in the system.");
    }
}
