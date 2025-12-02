package services;

// feat(gpa): convert percentage to 4.0 scale and compute cumulative GPA
import java.util.List;
public class GPACalculator {
    // Converts a single percentage grade to GPA points (4.0 scale)
    public double toGpa(double percentage) {
        if (percentage >= 93) return 4.0;
        if (percentage >= 90) return 3.7;
        if (percentage >= 87) return 3.3;
        if (percentage >= 83) return 3.0;
        if (percentage >= 80) return 2.7;
        if (percentage >= 77) return 2.3;
        if (percentage >= 73) return 2.0;
        if (percentage >= 70) return 1.7;
        if (percentage >= 67) return 1.3;
        if (percentage >= 60) return 1.0;
        return 0.0;
    }

    // Calculates cumulative GPA from a list of percentage grades
    public double cumulativeGpa(List<Double> percentages) {
        if (percentages == null || percentages.isEmpty()) return 0.0;
        double sum = 0;
        for (double p : percentages) sum += toGpa(p);
        return sum / percentages.size();
    }

    // Returns common letter grades (extended with +/-)
    public String toLetter(double percentage) {
        if (percentage >= 93) return "A";
        if (percentage >= 90) return "A-";
        if (percentage >= 87) return "B+";
        if (percentage >= 83) return "B";
        if (percentage >= 80) return "B-";
        if (percentage >= 77) return "C+";
        if (percentage >= 73) return "C";
        if (percentage >= 70) return "C-";
        if (percentage >= 67) return "D+";
        if (percentage >= 60) return "D";
        return "F";
    }
}
