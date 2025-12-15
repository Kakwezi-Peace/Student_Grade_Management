package exception;

public class ReportExportException extends Exception {
    public ReportExportException(String message) {
        super("Failed to export report: " + message);
    }
}
