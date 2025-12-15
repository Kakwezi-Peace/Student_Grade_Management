package exception;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String filename) {
        super("Invalid file format for file: " + filename + ". Expected CSV format.");
    }
}
