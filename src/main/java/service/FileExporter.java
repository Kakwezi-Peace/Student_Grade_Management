package service;

import exception.ReportExportException;
import util.LoggerUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileExporter {
    private final File reportsDir;

    public FileExporter(File reportsDir) {
        this.reportsDir = reportsDir;
        if (!reportsDir.exists()) {
            boolean created = reportsDir.mkdirs();
            if (created) LoggerUtil.info("Created reports directory: " + reportsDir.getAbsolutePath());
        }
    }

    public File exportText(String filenameWithoutExt, String content) throws ReportExportException {
        if (filenameWithoutExt == null || filenameWithoutExt.trim().isEmpty()) {
            throw new ReportExportException("Filename cannot be empty.");
        }
        File out = new File(reportsDir, filenameWithoutExt + ".txt");
        try (FileWriter writer = new FileWriter(out)) {
            writer.write(content);
        } catch (IOException e) {
            throw new ReportExportException(e.getMessage());
        }
        return out;
    }
}
