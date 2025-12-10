package service;

import java.util.ArrayList;
import java.util.List;
// class simple csvparser class
public class SimpleCSVParser implements CSVParser {
    @Override
    public List<String[]> parseLines(String csvContent) {
        List<String[]> rows = new ArrayList<>();
        if (csvContent == null) return rows;

        String[] lines = csvContent.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] parts = trimmed.split("\\s*,\\s*");
            if (parts.length == 4) {
                rows.add(parts);
            }
        }
        return rows;
    }
}
