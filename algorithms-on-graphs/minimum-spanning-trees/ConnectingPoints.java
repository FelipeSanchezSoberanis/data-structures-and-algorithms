import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConnectingPoints {
    private static MyLogger LOGGER = new MyLogger(ConnectingPoints.class.getName());

    private static double minimumDistance(int[] x, int[] y) {
        LOGGER.info("minimumDistance(int[] x, int[] y)");
        LOGGER.infoFormat("x: %s", Arrays.toString(x));
        LOGGER.infoFormat("y: %s", Arrays.toString(y));

        double result = 0;
        // write your code here

        LOGGER.info("");
        return result;
    }

    public static void main(String[] args) {

        DataReader dataReader = new DataReader();
        dataReader.readData();

        System.out.format("%.9f", minimumDistance(dataReader.getX(), dataReader.getY()));
    }
}

class DataReader {
    private int[] x;
    private int[] y;

    public void readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        x = new int[n];
        y = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }

        scanner.close();
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
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
}
