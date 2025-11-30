package services;

import exceptions.InvalidFileFormatException;

public interface CSVParser {
    // returns rows of [studentId, subjectName, subjectType, gradeString]
    String[][] parse(String path) throws InvalidFileFormatException;
}

