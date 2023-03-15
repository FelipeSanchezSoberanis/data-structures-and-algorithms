import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Reachability {
    private static MyLogger LOGGER;

    private static boolean reach(List<List<Integer>> adj, int x, int y) {
        LOGGER.infoFormat("Adjacent list: %s", adj.toString());
        LOGGER.infoFormat("x: %s", x);
        LOGGER.infoFormat("y: %s", y);

        boolean[] isVisited = new boolean[adj.size()];
        Arrays.fill(isVisited, false);

        int[] ccList = new int[adj.size()];
        Arrays.fill(ccList, -1);

        int cc = 0;

        for (int i = 0; i < adj.size(); i++) {
            if (!isVisited[i]) {
                explore(i, adj, isVisited, ccList, cc);
                cc++;
            }
        }

        return ccList[x] == ccList[y];
    }

    private static void explore(
            int v, List<List<Integer>> adj, boolean[] isVisited, int[] ccList, int cc) {
        LOGGER.infoFormat("Exploring node: %s", v);

        isVisited[v] = true;
        ccList[v] = cc;
        for (int w : adj.get(v)) {
            if (!isVisited[w]) explore(w, adj, isVisited, ccList, cc);
        }
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Reachability.class.getName());

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj.get(x - 1).add(y - 1);
            adj.get(y - 1).add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;

        scanner.close();

        System.out.println(reach(adj, x, y) ? 1 : 0);
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
