import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BFS {
    private static MyLogger LOGGER;

    private static List<List<Integer>> readGraphConnections(Scanner scanner) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int x, y;
        for (int i = 0; i < m; i++) {
            x = scanner.nextInt();
            y = scanner.nextInt();

            adj.get(x - 1).add(y - 1);
            adj.get(y - 1).add(x - 1);
        }

        return adj;
    }

    private static int[] readConnectionToSearch(Scanner scanner) {
        int from = scanner.nextInt();
        int to = scanner.nextInt();

        int[] fromAndTo = {from, to};
        return fromAndTo;
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(BFS.class.getName());

        Scanner scanner = new Scanner(System.in);

        List<List<Integer>> adj = readGraphConnections(scanner);
        int[] fromAndTo = readConnectionToSearch(scanner);

        scanner.close();

        int from = fromAndTo[0];
        int to = fromAndTo[1];

        Graph graph = new Graph(adj);
        int pathLength = graph.getPathLength(from - 1, to - 1);

        System.out.println(pathLength);
    }
}
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
