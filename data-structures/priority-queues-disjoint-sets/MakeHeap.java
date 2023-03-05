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
    private static Logger LOGGER;

    public static void main(String[] args) {
        LOGGER = new MyLogger(MakeHeap.class).getLogger();

        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        List<Integer> numbers = new ArrayList<>(noNumbers);

        for (int i = 0; i < noNumbers; i++) {
            numbers.add(scanner.nextInt());
        }

        scanner.close();

        CompleteBinaryTree binaryTree = new CompleteBinaryTree(numbers);

        LOGGER.info("=== Before sorting ===");
        LOGGER.info(String.format("Binary tree items: %s", binaryTree.toString()));

        binaryTree.sort();

        LOGGER.info("=== After sorting ===");
        LOGGER.info(String.format("Binary tree items: %s", binaryTree.toString()));
    }
}

class CompleteBinaryTree {
    private static Logger LOGGER;

    private List<Integer> items;

    public CompleteBinaryTree(List<Integer> items) {
        LOGGER = new MyLogger(CompleteBinaryTree.class).getLogger();

        this.items = new ArrayList<>(items.size());
        this.items = items;
    }

    public void sort() {
        for (int i = items.size() / 2 - 1; i >= 0; i--) {
            heapify(items.size(), i);
        }

        for (int i = items.size() - 1; i > 0; i--) {
            swap(0, i);
            heapify(i, 0);
        }
    }

    private void heapify(int heapSize, int rootValueIndex) {
        LOGGER.info("=== heapify ===");

        int largestValueIndex = rootValueIndex;

        if (getLeftIndex(rootValueIndex) < heapSize
                && getLeft(rootValueIndex).get() > get(largestValueIndex)) {
            largestValueIndex = getLeftIndex(rootValueIndex);
        }

        if (getRightIndex(rootValueIndex) < heapSize
                && getRight(rootValueIndex).get() > get(largestValueIndex)) {
            largestValueIndex = getRightIndex(rootValueIndex);
        }

        LOGGER.info(String.format("Root value index: %s", rootValueIndex));
        LOGGER.info(String.format("Root value: %s", get(rootValueIndex)));
        LOGGER.info(String.format("Left value: %s", getLeft(rootValueIndex)));
        LOGGER.info(String.format("Right value: %s", getRight(rootValueIndex)));
        LOGGER.info(String.format("Largest value of the two: %s", get(largestValueIndex)));
        LOGGER.info(String.format("Largest value index: %s", largestValueIndex));

        if (rootValueIndex != largestValueIndex) {
            swap(rootValueIndex, largestValueIndex);

            heapify(heapSize, largestValueIndex);
        }
    }

    private void swap(int i, int j) {
        LOGGER.info(String.format("=== Swapping ==="));

        LOGGER.info(
                String.format(
                        "item[%s] and item[%s] before swapping: %s, %s", i, j, get(i), get(j)));

        Integer swap = items.get(i);
        items.set(i, items.get(j));
        items.set(j, swap);

        LOGGER.info(
                String.format(
                        "item[%s] and item[%s] after swapping: %s, %s", i, j, get(i), get(j)));
    }

    private boolean isValidIndex(int i) {
        return i >= 0 && i < items.size();
    }

    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }

    private int getRightIndex(int i) {
        return 2 * i + 2;
    }

    private Integer get(int i) {
        return items.get(i);
    }

    private Optional<Integer> getLeft(int i) {
        int leftIndex = getLeftIndex(i);

        if (!isValidIndex(leftIndex)) return Optional.empty();

        return Optional.of(items.get(leftIndex));
    }

    private Optional<Integer> getRight(int i) {
        int rightIndex = getRightIndex(i);

        if (!isValidIndex(rightIndex)) return Optional.empty();

        return Optional.of(items.get(rightIndex));
    }

    private Optional<Integer> getParent(int i) {
        int parentIndex = Math.floorDiv(i - 1, 2);

        if (!isValidIndex(parentIndex)) return Optional.empty();

        return Optional.of(items.get(parentIndex));
    }

    @Override
    public String toString() {
        return items.toString();
    }
}

class MyLogger {
    private static Logger LOGGER;
    private static Level LOGGER_LEVEL = Level.INFO;

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

    public <T> MyLogger(Class<T> myClass) {
        LOGGER = Logger.getLogger(myClass.getName());

        configureLogger(LOGGER);
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
