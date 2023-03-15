import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Toposort {
    private static MyLogger LOGGER;

    private static List<List<Integer>> adj;
    private static boolean[] isVisited;
    private static List<Integer> order;

    private static void generateOrder() {
        LOGGER.infoFormat("Adjacent list: %s", adj.toString());

        order = new ArrayList<>(adj.size());

        isVisited = new boolean[adj.size()];
        Arrays.fill(isVisited, false);

        for (int i = 0; i < adj.size(); i++) {
            if (!isVisited[i]) {
                explore(i);
            }
        }

        order.sort(Collections.reverseOrder());
    }

    private static void explore(int u) {
        LOGGER.infoFormat("Exploring node: %s", u);

        isVisited[u] = true;
        for (int v : adj.get(u)) {
            if (!isVisited[v]) explore(v);
        }

        order.add(u);
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Toposort.class.getName());

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int x, y;
        for (int i = 0; i < m; i++) {
            x = scanner.nextInt();
            y = scanner.nextInt();

            adj.get(x - 1).add(y - 1);
        }

        scanner.close();

        generateOrder();

        order.forEach(v -> System.out.format("%s ", v + 1));
    }
}

class MyLogger {
    private Logger logger;
    private Level loggerLevel;

    public MyLogger(String className) {
        this.logger = Logger.getLogger(className);
        this.loggerLevel = Level.OFF;

        configureLogger();
    }

    private void configureLogger() {
        logger.setLevel(loggerLevel);

        logger.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%s] - %s", logRecord.getLevel(), logRecord.getMessage());
                    }
                };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);

        logger.addHandler(consoleHandler);
    }

    public void infoFormat(String stringToFormat, Object... variables) {
        logger.info(String.format(stringToFormat + "\n", variables));
    }
}
