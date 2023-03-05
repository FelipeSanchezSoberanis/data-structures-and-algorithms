import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        int[] numbers = new int[noNumbers];

        for (int i = noNumbers - 1; i >= 0; i--) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        CompleteBinaryTree tree = new CompleteBinaryTree(numbers);

        List<Swap> swaps = tree.getSwaps();
        System.out.println(swaps.size());
        swaps.forEach(s -> System.out.format("%s %s \n", s.getFirstNumber(), s.getSecondNumber()));

        LOGGER.info("=== General data ===");
        LOGGER.info(String.format("Numbers array, as inputed: %s", Arrays.toString(numbers)));
        LOGGER.info(String.format("Created heap: %s", tree.toString()));
        LOGGER.info(String.format("Valid heap? %s", tree.checkIfHeap()));
    }
}

class CompleteBinaryTree {
    private static Logger LOGGER;

    private int[] nodes;
    private int size;
    private List<Swap> swaps;

    public CompleteBinaryTree(int[] nodes) {
        LOGGER = new MyLogger(CompleteBinaryTree.class).getLogger();

        this.nodes = nodes;
        this.size = nodes.length;
        this.swaps = new ArrayList<>();

        buildHeap();
    }

    public boolean checkIfHeap() {
        for (int i = 0; i < size; i++) {
            int leftChildIndex = getLeftChildIndex(i);
            int rightChildIndex = getRightChildIndex(i);

            if (leftChildIndex < size && get(leftChildIndex) > get(i)) {
                return false;
            }

            if (rightChildIndex < size && get(rightChildIndex) > get(i)) {
                return false;
            }
        }

        return true;
    }

    public void sort() {
        int originalSize = size;
        for (int i = 0; i < originalSize - 1; i++) {
            swapNodes(0, size - 1);
            size--;
            siftDown(0);
        }
    }

    public void buildHeap() {
        for (int i = Math.floorDiv(size, 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public int remove(int i) {
        int value = get(i);
        set(i, Integer.MAX_VALUE);
        siftUp(i);
        extractMax();
        return value;
    }

    public int extractMax() {
        int result = get(0);
        set(0, get(size - 1));
        size--;
        siftDown(0);
        return result;
    }

    public void siftDown(int i) {
        int maxIndex = i;
        int leftChildIndex = getLeftChildIndex(i);
        int rightChildIndex = getRightChildIndex(i);

        if (leftChildIndex < size && get(leftChildIndex) > get(maxIndex)) {
            maxIndex = leftChildIndex;
        }

        if (rightChildIndex < size && get(rightChildIndex) > get(maxIndex)) {
            maxIndex = rightChildIndex;
        }

        if (i != maxIndex) {
            swapNodes(i, maxIndex);
            siftDown(maxIndex);
        }
    }

    public void siftUp(int i) {
        int parentIndex = getParentIndex(i);
        while (i > 0 && get(i) > get(parentIndex)) {
            swapNodes(i, parentIndex);
            i = parentIndex;
        }
    }

    public void swapNodes(int i, int j) {
        int swapValue = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = swapValue;

        swaps.add(new Swap(i, j));
    }

    public int getLeftChildIndex(int i) {
        return 2 * i + 1;
    }

    public int getRightChildIndex(int i) {
        return 2 * i + 2;
    }

    public int getParentIndex(int i) {
        return Math.floorDiv(i - 1, 2);
    }

    public int get(int i) {
        return nodes[i];
    }

    public void set(int i, int value) {
        nodes[i] = value;
    }

    public List<Swap> getSwaps() {
        return this.swaps;
    }

    @Override
    public String toString() {
        return Arrays.toString(nodes);
    }
}

class Swap {
    private int firstNumber;
    private int secondNumber;

    public Swap(int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    @Override
    public String toString() {
        return "Swap [firstNumber=" + firstNumber + ", secondNumber=" + secondNumber + "]";
    }
}

class MyLogger {
    private Logger LOGGER;
    private Level LOGGER_LEVEL = Level.INFO;

    private void configureLogger() {
        LOGGER.setLevel(LOGGER_LEVEL);

        LOGGER.setUseParentHandlers(false);

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

        LOGGER.addHandler(consoleHandler);
    }

    public <T> MyLogger(Class<T> myClass) {
        LOGGER = Logger.getLogger(myClass.getName());

        configureLogger();
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
