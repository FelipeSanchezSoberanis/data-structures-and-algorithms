import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dijkstra {
    private static MyLogger LOGGER;

    private static long distance(List<List<Integer>> adj, int[][] cost, int from, int to) {
        LOGGER.info("=== General data ===");
        LOGGER.infoFormat("adj: %s", adj.toString());
        LOGGER.infoFormat("cost: %s", cost.toString());
        LOGGER.infoFormat("from: %s", from);
        LOGGER.infoFormat("to: %s", to);
        LOGGER.info();

        long[] dist = new long[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[from] = 0;

        Map<Integer, Long> priorities = new HashMap<>();
        PriorityQueue<PriorityQueueNode> queue = new PriorityQueue<>(adj.size());

        for (int i = 0; i < adj.size(); i++) {
            priorities.put(i, dist[i]);
            queue.add(new PriorityQueueNode(i, priorities.get(i)));
        }

        LOGGER.info("=== Priority queue ===");
        LOGGER.infoFormat("Original priority queue: %s", queue.toString());
        LOGGER.info();

        while (!queue.isEmpty()) {
            PriorityQueueNode topNode = queue.poll();
            int u = topNode.getValue();

            if (topNode.getPriority() != priorities.get(u)) continue;

            for (int v : adj.get(u)) {
                int connCost = cost[u][v];
                if (dist[v] > dist[u] + connCost) {
                    dist[v] = dist[u] + connCost;

                    LOGGER.info("=== Updating priority ===");
                    LOGGER.infoFormat(
                            "Updating priority for %s from %s to %s",
                            v, priorities.get(v), dist[v]);
                    LOGGER.infoFormat("Priority queue before: %s", queue.toString());

                    priorities.put(v, dist[v]);
                    queue.add(new PriorityQueueNode(v, priorities.get(v)));

                    LOGGER.infoFormat("Priority queue after: %s", queue.toString());
                    LOGGER.info();
                }
            }
        }

        return dist[to] != Integer.MAX_VALUE ? dist[to] : -1;
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Dijkstra.class.getName());

        DataReader dr = new DataReader();
        dr.readData();

        System.out.println(distance(dr.getAdj(), dr.getCost(), dr.getX(), dr.getY()));
    }
}

class PriorityQueueNode implements Comparable<PriorityQueueNode> {
    private int value;
    private long priority;

    public PriorityQueueNode(int value, long priority) {
        this.value = value;
        this.priority = priority;
    }

    public PriorityQueueNode() {}

    public long getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(PriorityQueueNode node) {
        return Long.valueOf(this.priority).compareTo(Long.valueOf(node.getPriority()));
    }
}

class DataReader {
    private List<List<Integer>> adj;
    private int[][] cost;
    private int x;
    private int y;

    public void readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        adj = new ArrayList<>(n);
        cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int x, y, w;

            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();

            adj.get(x - 1).add(y - 1);
            cost[x - 1][y - 1] = w;
        }

        x = scanner.nextInt() - 1;
        y = scanner.nextInt() - 1;

        scanner.close();
    }

    public List<List<Integer>> getAdj() {
        return adj;
    }

    public int[][] getCost() {
        return cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void info(String info) {
        logger.info(info + "\n");
    }

    public void info() {
        logger.info("\n");
    }
}
