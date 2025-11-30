package exceptions;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String id) {
        super("Student with ID '" + id + "' not found.");
    }
}
