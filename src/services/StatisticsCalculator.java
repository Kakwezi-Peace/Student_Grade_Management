package services;


import grade.Grade;

import java.util.*;

public class StatisticsCalculator {
    // s Only performs statistical calculations
    public double calculateMean(List<Grade> grades) {
        return grades.stream().mapToDouble(Grade::getGrade).average().orElse(0);
    }

    public double calculateMedian(List<Grade> grades) {
        List<Double> sorted = grades.stream().map(Grade::getGrade).sorted().toList();
        int n = sorted.size();
        if (n % 2 == 0) {
            return (sorted.get(n/2 - 1) + sorted.get(n/2)) / 2.0;
        } else {
            return sorted.get(n/2);
        }
    }

    public double calculateStandardDeviation(List<Grade> grades) {
        double mean = calculateMean(grades);
        double variance = grades.stream()
                .mapToDouble(g -> Math.pow(g.getGrade() - mean, 2))
                .average().orElse(0);
        return Math.sqrt(variance);
    }
}
