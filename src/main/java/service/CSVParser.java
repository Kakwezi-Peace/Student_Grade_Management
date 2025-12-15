package service;

import java.util.List;

public interface CSVParser {
    List<String[]> parseLines(String csvContent);
}
