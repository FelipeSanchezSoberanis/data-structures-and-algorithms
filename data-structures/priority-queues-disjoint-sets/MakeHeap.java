import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<Integer> numbers = new ArrayList<>(noNumbers);

        for (int i = 0; i < noNumbers; i++) {
            numbers.add(scanner.nextInt());
        }

        scanner.close();

        LOGGER.info(String.format("Numbers: %s", numbers.toString()));

        CompleteBinaryTree<Integer> binaryTree = new CompleteBinaryTree<>(numbers);
    }
}

class CompleteBinaryTree<T> {
    private List<T> items;

    public CompleteBinaryTree(List<T> items) {
        this.items = new ArrayList<>(items.size());
        this.items = items;
    }

    private boolean isValidIndex(int i) {
        return i >= 0 || i < items.size();
    }

    public T get(int i) {
        return items.get(i);
    }

    public Optional<T> getLeft(int i) {
        int leftIndex = 2 * i + 1;

        if (!isValidIndex(leftIndex)) return Optional.empty();

        return Optional.of(items.get(leftIndex));
    }

    public Optional<T> getRight(int i) {
        int rightIndex = 2 * i + 2;

        if (!isValidIndex(rightIndex)) return Optional.empty();

        return Optional.of(items.get(rightIndex));
    }

    public Optional<T> getParent(int i) {
        int parentIndex = Math.floorDiv(i - 1, 2);

        if (!isValidIndex(parentIndex)) return Optional.empty();

        return Optional.of(items.get(parentIndex));
    }
}
