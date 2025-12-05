package exceptions;

// Custom checked exception for invalid grade values
public class InvalidGradeException extends Exception {
    public InvalidGradeException(int grade) {
        super("Grade must be between 0 and 100. You entered: " + grade);
    }
}
