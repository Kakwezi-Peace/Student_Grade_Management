package services;

import exceptions.InvalidFileFormatException;
import grade.Grade;
import grade.GradeManager;
import manager.StudentManager;
import student.Student;
import subject.Subject;
import utils.LoggerUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * High-level bulk import orchestrator.
 * DIP: depends on CSVParser abstraction injected via constructor.
 * SRP: Handles validation, importing, and logging summary.
 */
public class BulkImportService {

    private final CSVParser parser;
    private final StudentManager studentManager;
    private final GradeManager gradeManager;

    public BulkImportService(CSVParser parser, StudentManager studentManager, GradeManager gradeManager) {
        this.parser = parser;
        this.studentManager = studentManager;
        this.gradeManager = gradeManager;
    }

    public ImportResult importGrades(String filenameNoExt) {
        String filePath = "./imports/" + filenameNoExt + ".csv";
        ImportResult result = new ImportResult();

        System.out.println("Validating file");
        List<GradeRow> rows;
        try {
            rows = parser.parse(filePath);
        } catch (InvalidFileFormatException e) {
            System.out.println("❌ Invalid CSV format: " + e.getMessage());
            LoggerUtil.logError(e);
            result.setSummary("Failed: Invalid CSV format — " + e.getMessage());
            writeLog(result, filenameNoExt);
            return result;
        }

        System.out.println("Processing grades");
        int rowIndex = 0;
        for (GradeRow r : rows) {
            rowIndex++;
            String context = "Row " + rowIndex + ": " + r.getStudentId() + ", " + r.getSubjectName()
                    + ", " + r.getSubjectType() + ", " + r.getGrade();

            // Validate student
            Student student = studentManager.findStudent(r.getStudentId());
            if (student == null) {
                String msg = "Invalid student ID (" + r.getStudentId() + ")";
                result.addFailure(context, msg);
                LoggerUtil.logError(new Exception(msg));
                continue;
            }

            // Validate grade range
            if (r.getGrade() < 0 || r.getGrade() > 100) {
                String msg = "Grade out of range (" + r.getGrade() + ")";
                result.addFailure(context, msg);
                LoggerUtil.logError(new Exception(msg));
                continue;
            }

            // Validate subject
            Subject subject = SubjectResolver.resolve(r.getSubjectName(), r.getSubjectType());
            if (subject == null) {
                String msg = "Invalid subject/type (" + r.getSubjectName() + ", " + r.getSubjectType() + ")";
                result.addFailure(context, msg);
                LoggerUtil.logError(new Exception(msg));
                continue;
            }

            // Record grade
            Grade grade = new Grade(r.getStudentId(), subject, r.getGrade());
            gradeManager.recordGrade(grade);

            result.incrementSuccess();
        }

        result.setTotal(rows.size());
        result.setSummary("Successfully Imported: " + result.getSuccess()
                + " | Failed: " + result.getFailures().size());

        writeLog(result, filenameNoExt);

        System.out.println();
        System.out.println("IMPORT SUMMARY");
        System.out.println("Total Rows: " + result.getTotal());
        System.out.println("Successfully Imported: " + result.getSuccess());
        System.out.println("Failed: " + result.getFailures().size());
        if (!result.getFailures().isEmpty()) {
            System.out.println("Failed Records:");
            result.getFailures().forEach(fr ->
                    System.out.println(fr.rowContext() + " => " + fr.reason()));
        }
        System.out.println();
        System.out.println("☒ Import completed! " + result.getSuccess() + " grades added to system");
        System.out.println("See " + result.getLogFilename() + " for details");

        return result;
    }

    private void writeLog(ImportResult result, String filenameNoExt) {
        String dateStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String logFilename = "./imports/import_log_" + dateStamp + ".txt";
        result.setLogFilename(logFilename);
        try (FileWriter writer = new FileWriter(logFilename, true)) {
            writer.write("==== IMPORT LOG (" + filenameNoExt + ") ====\n");
            writer.write("Summary: " + result.getSummary() + "\n");
            writer.write("Total Rows: " + result.getTotal() + "\n");
            writer.write("Success: " + result.getSuccess() + "\n");
            writer.write("Failures: " + result.getFailures().size() + "\n");
            if (!result.getFailures().isEmpty()) {
                writer.write("Failed Details:\n");
                for (ImportFailure f : result.getFailures()) {
                    writer.write("- " + f.rowContext() + " :: " + f.reason() + "\n");
                }
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("❌ Failed to write import log: " + e.getMessage());
            LoggerUtil.logError(e);
        }
    }
}
