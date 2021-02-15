package flashcards;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static List<String> logs = new ArrayList<>();


    public static void logConsoleInput(String log) {
        logs.add(log);
    }

    public static void logConsoleOutput(String log) {
        System.out.println(log);
        logs.add(log);
    }

    public static List<String> getLogs() {
        return logs;
    }
}
