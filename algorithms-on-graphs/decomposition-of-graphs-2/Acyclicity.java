import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Acyclicity {
    private static MyLogger LOGGER;

    private static int clock;
    private static int[] pre;
    private static int[] post;
    private static List<List<Integer>> adj;
    private static boolean[] isVisited;

    private static boolean acyclic() {
        LOGGER.infoFormat("Adjacent list: %s", adj.toString());

        isVisited = new boolean[adj.size()];
        Arrays.fill(isVisited, false);

        pre = new int[adj.size()];
        post = new int[adj.size()];

        clock = 1;
        for (int i = 0; i < adj.size(); i++) {
            if (!isVisited[i]) {
                explore(i);
            }
        }

        LOGGER.infoFormat("Previsits: %s", Arrays.toString(pre));
        LOGGER.infoFormat("Postvisits: %s", Arrays.toString(post));

        boolean hasCycle = false;
        for (int u = 0; u < adj.size(); u++) {
            List<Integer> vs = adj.get(u);
            for (int v : vs) {
                if (post[u] <= post[v]) hasCycle = true;
            }
        }

        return hasCycle;
    }

    private static void explore(int u) {
        LOGGER.infoFormat("Exploring node: %s", u);

        isVisited[u] = true;

        pre[u] = clock;
        clock++;

        for (int v : adj.get(u)) {
            if (!isVisited[v]) explore(v);
        }

        post[u] = clock;
        clock++;
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Acyclicity.class.getName());

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

        System.out.println(acyclic() ? 1 : 0);
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
