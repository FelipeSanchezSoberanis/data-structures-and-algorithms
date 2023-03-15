import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Acyclicity {
    private static MyLogger LOGGER;

    private static int acyclic(List<List<Integer>> adj) {
        LOGGER.infoFormat("adj: %s", adj);

        return 0;
    }

    public static void main(String[] args) {
        LOGGER = new MyLogger(Acyclicity.class.getName());

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

        System.out.println(acyclic(adj));
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
