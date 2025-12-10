package service;

import exception.InvalidFileFormatException;
import exception.InvalidGradeException;
import grade.Grade;
import grade.GradeManager;
import model.CoreSubject;
import model.ElectiveSubject;
import model.Subject;
import model.Student;
import student.StudentManager;
import util.ValidationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class BulkImportService {
    private final StudentManager studentManager;
    private final GradeManager gradeManager;
    private final CSVParser parser;
    private final File importsDir;
    private final File logsDir;

    // BulkImportService method
    public BulkImportService(StudentManager sm, GradeManager gm, CSVParser parser, File importsDir, File logsDir) {
        this.studentManager = sm;
        this.gradeManager = gm;
        this.parser = parser;
        this.importsDir = importsDir;
        this.logsDir = logsDir;
        if (!importsDir.exists()) importsDir.mkdirs();
        if (!logsDir.exists()) logsDir.mkdirs();
    }

    public ImportResult importFile(String filenameWithoutExt) throws InvalidFileFormatException {
        File file = new File(importsDir, filenameWithoutExt + ".csv");
        if (!file.exists()) throw new InvalidFileFormatException(file.getName());

        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new InvalidFileFormatException(file.getName());
        }

        List<String[]> rows = parser.parseLines(content);
        int success = 0, failure = 0;
        StringBuilder log = new StringBuilder();

        int rowNum = 0;
        for (String[] r : rows) {
            rowNum++;
            if (r.length != 4) {
                failure++;
                log.append("Row ").append(rowNum).append(": Invalid format\n");
                continue;
            }

            String sid = r[0].trim();
            String subjectName = r[1].trim();
            String subjectType = r[2].trim();
            String gradeStr = r[3].trim();

            Student s = studentManager.findStudent(sid);
            if (s == null) {
                failure++;
                log.append("Row ").append(rowNum).append(": Invalid student ID (").append(sid).append(")\n");
                continue;
            }

            double gradeVal;
            try {
                gradeVal = Double.parseDouble(gradeStr);
                ValidationUtil.validateGrade(gradeVal);
            } catch (Exception ex) {
                failure++;
                log.append("Row ").append(rowNum).append(": Grade out of range (").append(gradeStr).append(")\n");
                continue;
            }

            Subject subject = subjectType.equalsIgnoreCase("Core")
                    ? new CoreSubject(subjectName, codeFor(subjectName))
                    : new ElectiveSubject(subjectName, codeFor(subjectName));

            Grade g = new Grade(sid, subject, gradeVal);
            if (gradeManager.addGrade(g)) {
                success++;
            } else {
                failure++;
                log.append("Row ").append(rowNum).append(": Capacity reached\n");
            }
        }

        // Write log file
        String logFilename = "import_log_" + System.currentTimeMillis() + ".txt";
        File logFile = new File(logsDir, logFilename);
        try (FileWriter writer = new FileWriter(logFile)) {
            writer.write(log.toString());
        } catch (IOException ignored) {}

        return new ImportResult(success, failure, logFile.getName());
    }

    private String codeFor(String subjectName) {
        return switch (subjectName.toLowerCase()) {
            case "mathematics" -> "MAT";
            case "english" -> "ENG";
            case "science" -> "SCI";
            case "music" -> "MUS";
            case "art" -> "ART";
            case "physical education" -> "PE";
            default -> subjectName.substring(0, Math.min(3, subjectName.length())).toUpperCase();
        };
    }

    public static class ImportResult {
        public final int successCount;
        public final int failureCount;
        public final String logFilename;

        public ImportResult(int successCount, int failureCount, String logFilename) {
            this.successCount = successCount;
            this.failureCount = failureCount;
            this.logFilename = logFilename;
        }
    }
}
