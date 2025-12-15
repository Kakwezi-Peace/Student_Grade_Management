package service;

import exception.ReportExportException;
import org.junit.jupiter.api.*;
import service.FileExporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileExporterTest {

    private File tempDir;
    private FileExporter fileExporter;

    @BeforeEach
    void setUp() {
        // Create temporary directory for testing
        tempDir = new File("test-reports");
        if (tempDir.exists()) {
            deleteDirectory(tempDir);
        }
        tempDir.mkdirs();

        fileExporter = new FileExporter(tempDir);
    }

    @AfterEach
    void tearDown() {
        deleteDirectory(tempDir);
    }

    // Utility to clean up test directory
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        directory.delete();
    }

    @Test
    void testExportTextCreatesFileWithCorrectContent() throws Exception {
        String filename = "report1";
        String content = "Hello, world!";

        File result = fileExporter.exportText(filename, content);

        assertTrue(result.exists(), "File should be created");
        assertEquals("report1.txt", result.getName());

        // Check written content
        String fileContent = Files.readString(result.toPath());
        assertEquals(content, fileContent);
    }

    @Test
    void testExportTextRejectsEmptyFilename() {
        assertThrows(ReportExportException.class, () ->
                fileExporter.exportText("  ", "test content"));
    }

    @Test
    void testReportsDirectoryCreatedAutomatically() {
        // Directory already exists because of setUp()
        assertTrue(tempDir.exists());
        assertTrue(tempDir.isDirectory());
    }

    @Test
    void testExportTextThrowsExceptionOnIOError() {
        // Use an invalid path that cannot be created or written
        File invalidDir = new File("Z:/this/path/does/not/exist");

        FileExporter badExporter = new FileExporter(invalidDir);

        assertThrows(ReportExportException.class, () -> {
            badExporter.exportText("report", "test content");
        });
    }


}