package service;

import grade.GradeManager;
import model.Student;
import student.StudentManager;

import java.util.*;

public class StatisticsCalculator {

    // Convert percentage to letter grade
    public String toLetter(double pct) {
        if (pct >= 90) return "A";
        if (pct >= 80) return "B";
        if (pct >= 70) return "C";
        if (pct >= 60) return "D";
        return "F";
    }

    // Count grade distribution
    public Map<String, Integer> gradeDistribution(double[] grades) {
        Map<String, Integer> dist = new LinkedHashMap<>();
        dist.put("A", 0); dist.put("B", 0); dist.put("C", 0); dist.put("D", 0); dist.put("F", 0);
        for (double g : grades) {
            String letter = toLetter(g);
            dist.put(letter, dist.get(letter) + 1);
        }
        return dist;
    }
// how to find mean
    public double mean(double[] values) {
        if (values.length == 0) return 0.0;
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }
// how to find the median
    public double median(double[] values) {
        if (values.length == 0) return 0.0;
        double[] copy = Arrays.copyOf(values, values.length);
        Arrays.sort(copy);
        int n = copy.length;
        if (n % 2 == 1) return copy[n / 2];
        return (copy[n / 2 - 1] + copy[n / 2]) / 2.0;
    }
// how to find mode
    public double mode(double[] values) {
        if (values.length == 0) return 0.0;
        Map<Double, Integer> freq = new HashMap<>();
        for (double v : values) freq.put(v, freq.getOrDefault(v, 0) + 1);
        double mode = values[0]; int max = 1;
        for (Map.Entry<Double, Integer> e : freq.entrySet()) {
            if (e.getValue() > max) {
                mode = e.getKey(); max = e.getValue();
            }
        }
        return mode;
    }

     // methods
    public double stdDev(double[] values) {
        if (values.length == 0) return 0.0;
        double mu = mean(values);
        double sumSq = 0;
        for (double v : values) sumSq += (v - mu) * (v - mu);
        return Math.sqrt(sumSq / values.length);
    }

    public double[] getAllGrades(StudentManager sm, GradeManager gm) {
        List<Double> all = new ArrayList<>();
        for (Student s : sm.getAllStudents()) {
            double[] grades = gm.getGradesForStudent(s.getStudentId());
            for (double g : grades) all.add(g);
        }
        return all.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getGradesByType(StudentManager sm, GradeManager gm, String type) {
        List<Double> filtered = new ArrayList<>();
        for (Student s : sm.getAllStudents()) {
            if (s.getStudentType().equalsIgnoreCase(type)) {
                double[] grades = gm.getGradesForStudent(s.getStudentId());
                for (double g : grades) filtered.add(g);
            }
        }
        return filtered.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getGradesBySubjectType(StudentManager sm, GradeManager gm, String subjectType) {
        List<Double> filtered = new ArrayList<>();
        for (Student s : sm.getAllStudents()) {
            double[] grades = gm.getGradesForStudent(s.getStudentId());
            for (double g : grades) filtered.add(g); // Simplified: assumes all grades are valid
        }
        return filtered.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
