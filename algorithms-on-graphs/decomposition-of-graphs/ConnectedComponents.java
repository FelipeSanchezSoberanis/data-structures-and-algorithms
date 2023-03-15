import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConnectedComponents {
    private static MyLogger LOGGER;

    private static int getNumConnectedComponents(List<List<Integer>> adj) {
        LOGGER.infoFormat("Adjacent list: %s", adj.toString());

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

        return cc;
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
        LOGGER = new MyLogger(ConnectedComponents.class.getName());

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

        scanner.close();

        System.out.println(getNumConnectedComponents(adj));
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
