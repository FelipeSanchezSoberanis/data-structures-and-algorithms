import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class NegativeCycle {
    private static boolean hasNegativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        Map<Integer, Integer> distance = new HashMap<>();

        for (int j = 0; j < adj.length - 1; j++) {
            for (int u = 0; u < adj.length; u++) {
                List<Integer> adjList = adj[u];
                List<Integer> costList = cost[u];

                for (int k = 0; k < adjList.size(); k++) {
                    int v = adjList.get(k);
                    int w = costList.get(k);

                    int dist = distance.getOrDefault(v, 0);
                    int newDist = distance.getOrDefault(u, 0) + w;

                    if (dist > newDist) distance.put(v, newDist);
                }
            }
        }

        for (int u = 0; u < adj.length; u++) {
            List<Integer> adjList = adj[u];
            List<Integer> costList = cost[u];
            for (int k = 0; k < adjList.size(); k++) {
                int v = adjList.get(k);
                int w = costList.get(k);

                int dist = distance.getOrDefault(v, 0);
                int newDist = distance.getOrDefault(u, 0) + w;

                if (dist > newDist) return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        DataReader dataReader = new DataReader();
        dataReader.readData();

        System.out.println(hasNegativeCycle(dataReader.getAdj(), dataReader.getCost()) ? 1 : 0);
    }
}

class DataReader {
    private ArrayList<Integer>[] adj;
    private ArrayList<Integer>[] cost;

    public void readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        adj = (ArrayList<Integer>[]) new ArrayList[n];
        cost = (ArrayList<Integer>[]) new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }

        int x, y, w;
        for (int i = 0; i < m; i++) {
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();

            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }

        scanner.close();
    }

    public ArrayList<Integer>[] getAdj() {
        return adj;
    }

    public ArrayList<Integer>[] getCost() {
        return cost;
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
