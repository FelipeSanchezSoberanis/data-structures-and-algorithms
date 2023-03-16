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

public class StronglyConnected {
    private static MyLogger LOGGER;

    private static List<List<Integer>> adj;
    private static List<List<Integer>> adjReversed;
    private static boolean[] isVisited;
    private static int[] post;
    private static int[] pre;
    private static int clock;
    private static List<VertexWithPostValue> vertexWithPostValues;

    private static int numStronglyConnectedComponents() {
        reverseGraph();

        LOGGER.infoFormat("Adjacent list: %s", adj.toString());
        LOGGER.infoFormat("Reversed adjacent list: %s", adjReversed.toString());

        depthFirstSearch();

        vertexWithPostValues = new ArrayList<>();
        for (int i = 0; i < post.length; i++) {
            vertexWithPostValues.add(new VertexWithPostValue(i, post[i]));
        }

        LOGGER.infoFormat(
                "Vertices with post values before sorting: %s", vertexWithPostValues.toString());
        Collections.sort(
                vertexWithPostValues, (a, b) -> a.getPostValue() < b.getPostValue() ? 1 : -1);
        LOGGER.infoFormat(
                "Vertices with post values after sorting: %s", vertexWithPostValues.toString());

        isVisited = new boolean[adjReversed.size()];
        Arrays.fill(isVisited, false);
        for (VertexWithPostValue vertexWithPostValue : vertexWithPostValues) {
            if (!isVisited[vertexWithPostValue.getVertexValue()])
                exploreVertex(vertexWithPostValue);
        }

        return 1;
    }

    private static void depthFirstSearch() {
        pre = new int[adjReversed.size()];
        post = new int[adjReversed.size()];
        isVisited = new boolean[adjReversed.size()];
        clock = 1;

        for (int v = 0; v < adjReversed.size(); v++) {
            if (!isVisited[v]) explore(v);
        }
    }

    private static void exploreVertex(VertexWithPostValue u) {
        isVisited[u.getVertexValue()] = true;
        for (VertexWithPostValue v : vertexWithPostValues) {}
    }

    private static void explore(int v) {
        LOGGER.infoFormat("Exploring node: %s", v);

        pre[v] = clock;
        clock++;

        isVisited[v] = true;
        for (int w : adj.get(v)) {
            if (!isVisited[w]) explore(w);
        }

        post[v] = clock;
        clock++;
    }

    private static void reverseGraph() {
        adjReversed = new ArrayList<>();
        for (int i = 0; i < adj.size(); i++) {
            adjReversed.add(new ArrayList<>());
        }

        for (int u = 0; u < adj.size(); u++) {
            for (int v : adj.get(u)) {
                adjReversed.get(v).add(u);
            }
        }
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(StronglyConnected.class.getName());

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

        System.out.println(numStronglyConnectedComponents());
    }
}

class VertexWithPostValue {
    private int vertexValue;
    private int postValue;

    public VertexWithPostValue(int vertexValue, int postValue) {
        this.vertexValue = vertexValue;
        this.postValue = postValue;
    }

    public int getVertexValue() {
        return vertexValue;
    }

    public void setVertexValue(int vertexValue) {
        this.vertexValue = vertexValue;
    }

    public int getPostValue() {
        return postValue;
    }

    public void setPostValue(int postValue) {
        this.postValue = postValue;
    }

    @Override
    public String toString() {
        return "VertexWithPostValue [postValue=" + postValue + ", vertexValue=" + vertexValue + "]";
    }
}

class MyLogger {
    private Logger logger;
    private Level loggerLevel;

    public MyLogger(String className) {
        this.logger = Logger.getLogger(className);
        this.loggerLevel = Level.INFO;

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
