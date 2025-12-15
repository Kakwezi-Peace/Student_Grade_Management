package util;

import exception.InvalidGradeException;

public final class ValidationUtil {
    private ValidationUtil() {}

    public static void validateGrade(double grade) throws InvalidGradeException {
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException(grade);
        }
    }

    public static boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
