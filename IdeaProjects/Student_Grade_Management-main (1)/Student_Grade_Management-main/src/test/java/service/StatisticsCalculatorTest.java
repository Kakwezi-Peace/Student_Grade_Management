package service;

import service.StatisticsCalculator;

public class StatisticsCalculatorTest {

    public static void main(String[] args) {
        StatisticsCalculator calculator = new StatisticsCalculator();

        // Test 1: Average calculation
        double[] grades1 = {80.0, 90.0, 70.0};
        double average = calculator.calculateAverage(grades1);
        System.out.println("Average test: " + (average == 80.0 ? "PASSED" : "FAILED"));

        // Test 2: Median calculation done
        double[] grades2 = {80.0, 90.0, 70.0, 85.0};
        double median = calculator.calculateMedian(grades2);
        System.out.println("Median test: " + (median == 82.5 ? "PASSED" : "FAILED"));

        // Add more tests here
        System.out.println("All tests completed!");
    }
}