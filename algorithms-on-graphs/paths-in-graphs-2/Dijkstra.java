import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dijkstra {
    private static MyLogger LOGGER;

    private static long distance(List<List<Integer>> adj, List<List<Integer>> cost, int s, int t) {
        LOGGER.infoFormat("Adj: %s", adj.toString());
        LOGGER.infoFormat("Cost: %s", cost.toString());
        LOGGER.infoFormat("s: %s", s);
        LOGGER.infoFormat("t: %s", t);

        return -1;
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Dijkstra.class.getName());

        DataReader dr = new DataReader();
        dr.readData();

        System.out.println(distance(dr.getAdj(), dr.getCost(), dr.getX(), dr.getY()));
    }
}

class DataReader {
    private List<List<Integer>> adj;
    private List<List<Integer>> cost;
    private int x;
    private int y;

    public void readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        adj = new ArrayList<>(n);
        cost = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            cost.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int x, y, w;

            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();

            adj.get(x - 1).add(y - 1);
            cost.get(x - 1).add(w);
        }

        x = scanner.nextInt() - 1;
        y = scanner.nextInt() - 1;

        scanner.close();
    }

    public List<List<Integer>> getAdj() {
        return adj;
    }

    public List<List<Integer>> getCost() {
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

    public void info(String info) {
        logger.info(info + "\n");
    }

    public void info() {
        logger.info("\n");
    }
}
