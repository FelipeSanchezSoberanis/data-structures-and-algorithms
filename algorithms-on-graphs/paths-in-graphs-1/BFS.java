import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BFS {
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

class Graph {
    private MyLogger LOGGER;

    private List<List<Integer>> adj;

    public Graph(List<List<Integer>> adj) {
        LOGGER = new MyLogger(Graph.class.getName());

        this.adj = adj;
    }

    public int getPathLength(int from, int to) {
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[from] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(from);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v : adj.get(u)) {
                if (dist[v] == Integer.MAX_VALUE) {
                    queue.add(v);
                    dist[v] = dist[u] + 1;
                    if (v == to) return dist[to] != Integer.MAX_VALUE ? dist[to] : -1;
                }
            }
        }

        return dist[to] != Integer.MAX_VALUE ? dist[to] : -1;
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
