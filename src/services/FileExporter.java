package services;

import exceptions.ExportFailedException;

import java.io.FileWriter;
import java.io.IOException;



public class FileExporter {
    // SRP: Only handles file operations
    public void exportToFile(String filename, String content) throws ExportFailedException, Throwable {
        try (FileWriter writer = new FileWriter("./reports/" + filename + ".txt")) {
            writer.write(content);
        } catch (IOException e) {
            throw new Throwable("Failed to export report: " + e.getMessage());
        }
    }
}
