import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class StronglyConnected {
    private static List<List<Integer>> readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int x, y;
        for (int i = 0; i < m; i++) {
            x = scanner.nextInt();
            y = scanner.nextInt();

            adj.get(x - 1).add(y - 1);
        }

        scanner.close();

        return adj;
    }

    public static void main(String[] args) {
        List<List<Integer>> adj = readData();
        Graph graph = new Graph(adj);

        System.out.println(graph.getNumStronglyConnComp());
    }
}

class Graph {
    private final MyLogger LOGGER;

    private List<List<Integer>> adj;

    public Graph(List<List<Integer>> adj) {
        this.LOGGER = new MyLogger(Graph.class.getName());

        this.adj = adj;
    }

    public int getNumStronglyConnComp() {
        Stack<Integer> vertexStack = depthFirstSearch();
        LOGGER.infoFormat("Vertex stack: %s", vertexStack.toString());

        List<List<Integer>> adjReversed = reverseAdjacentList(adj);

        boolean[] isVisited = new boolean[adjReversed.size()];
        Arrays.fill(isVisited, false);

        int strongConnCompCounter = 0;
        while (!vertexStack.isEmpty()) {
            int topVertex = vertexStack.pop();
            if (!isVisited[topVertex]) {
                explore(topVertex, isVisited, adjReversed);
                strongConnCompCounter++;
            }
        }

        return strongConnCompCounter;
    }

    private Stack<Integer> depthFirstSearch() {
        Stack<Integer> stack = new Stack<>();

        boolean[] isVisited = new boolean[adj.size()];
        Arrays.fill(isVisited, false);

        for (int i = 0; i < adj.size(); i++) {
            if (!isVisited[i]) explore(i, isVisited, stack);
        }

        return stack;
    }

    private void explore(int u, boolean[] isVisited, List<List<Integer>> adjReversed) {
        isVisited[u] = true;

        for (int v : adjReversed.get(u)) {
            if (!isVisited[v]) explore(v, isVisited, adjReversed);
        }
    }

    private void explore(int u, boolean[] isVisited, Stack<Integer> stack) {
        isVisited[u] = true;

        for (int v : adj.get(u)) {
            if (!isVisited[v]) explore(v, isVisited, stack);
        }

        stack.push(u);
    }

    private List<List<Integer>> reverseAdjacentList(List<List<Integer>> adj) {
        List<List<Integer>> adjReversed = new ArrayList<>();

        for (int i = 0; i < adj.size(); i++) adjReversed.add(new ArrayList<>());

        for (int u = 0; u < adj.size(); u++) {
            for (int v : adj.get(u)) {
                adjReversed.get(v).add(u);
            }
        }

        return adjReversed;
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
