package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerUtil {
    private LoggerUtil() {}

    public static void info(String message) {
        System.out.println(prefix() + "INFO  - " + message);
    }

    public static void warn(String message) {
        System.out.println(prefix() + "WARN  - " + message);
    }

    public static void error(String message) {
        System.err.println(prefix() + "ERROR - " + message);
    }

    private static String prefix() {
        return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
    }
}
