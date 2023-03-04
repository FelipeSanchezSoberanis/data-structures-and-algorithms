import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MakeHeap {
    private static final Logger LOGGER = Logger.getLogger(MakeHeap.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static void configureLogger(Logger logger) {
        logger.setLevel(LOGGER_LEVEL);

        logger.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%-7s] - %s \n", logRecord.getLevel(), logRecord.getMessage());
                    }
                };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);

        logger.addHandler(consoleHandler);
    }

    public static void main(String[] args) {
        configureLogger(LOGGER);

        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        LOGGER.info(String.format("Numbers: %s", Arrays.toString(numbers)));
    }
}
