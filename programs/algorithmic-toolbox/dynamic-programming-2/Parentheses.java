import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parentheses {
    private static final Logger LOGGER = Logger.getLogger(Parentheses.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static int[] numbers;
    private static char[] operations;

    private static int[][] M;
    private static int[][] m;

    private static void printMatrix(int[][] matrix) {
        int[] maxNumberPerRow = new int[matrix.length];
        int[] minNumberPerRow = new int[matrix.length];

        int counter = 0;
        for (int[] row : matrix) {
            maxNumberPerRow[counter] = Arrays.stream(row).max().getAsInt();
            minNumberPerRow[counter] = Arrays.stream(row).min().getAsInt();
            counter++;
        }

        int maxNumber = Arrays.stream(maxNumberPerRow).max().getAsInt();
        String maxNumberString = Integer.toString(maxNumber);

        int minNumber = Arrays.stream(minNumberPerRow).min().getAsInt();
        String minNumberString = Integer.toString(minNumber);

        int length = Math.max(maxNumberString.length(), minNumberString.length());

        System.out.println();
        System.out.println("Matrix:");

        for (int[] row : matrix) {
            for (int rowItem : row) {
                System.out.format("[%" + length + "d] ", rowItem);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }

    private static void parseInput(String input) {
        int numberDigits = (input.length() + 1) / 2;

        numbers = new int[numberDigits];
        operations = new char[numberDigits - 1];

        int counter = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 0) {
                char myChar = input.charAt(i);
                int myNumber = Integer.parseInt(Character.toString(myChar));
                numbers[counter] = myNumber;
            } else {
                operations[counter] = input.charAt(i);
                counter++;
            }
        }

        m = new int[numberDigits][numberDigits];
        M = new int[numberDigits][numberDigits];

        LOGGER.info(String.format("numbers: %s", Arrays.toString(numbers)));
        LOGGER.info(String.format("operations: %s", Arrays.toString(operations)));
    }

    private static int operate(int numberA, char operation, int numberB) {
        LOGGER.info(String.format("Number 1: %s", numberA));
        LOGGER.info(String.format("Number 2: %s", numberB));
        LOGGER.info(String.format("Operation: %s", operation));

        switch (operation) {
            case '+':
                LOGGER.info("Addition");
                return numberA + numberB;
            case '*':
                LOGGER.info("Multiplication");
                return numberA * numberB;
            case '-':
                LOGGER.info("Substraction");
                return numberA - numberB;
        }
        return 0;
    }

    private static int[] minAndMax(int i, int j) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        int a = 0, b = 0, c = 0, d = 0;
        for (int k = i; k < j; k++) {
            a = operate(M[i][k], operations[k], M[k + 1][j]);
            b = operate(M[i][k], operations[k], m[k + 1][j]);
            c = operate(m[i][k], operations[k], M[k + 1][j]);
            d = operate(m[i][k], operations[k], m[k + 1][j]);

            int[] optionsForMin = {min, a, b, c, d};
            int[] optionsForMax = {max, a, b, c, d};

            min = Arrays.stream(optionsForMin).min().getAsInt();
            max = Arrays.stream(optionsForMax).max().getAsInt();

            LOGGER.info(String.format("Options for min: %s", Arrays.toString(optionsForMin)));
            LOGGER.info(String.format("Min: %s", min));
            LOGGER.info(String.format("Options for max: %s", Arrays.toString(optionsForMax)));
            LOGGER.info(String.format("Max: %s", max));
        }

        int[] res = {min, max};
        return res;
    }

    private static int parentheses() {
        for (int i = 0; i < numbers.length; i++) {
            m[i][i] = numbers[i];
            M[i][i] = numbers[i];
        }

        for (int s = 1; s < numbers.length; s++) {
            for (int i = 0; i < numbers.length - s; i++) {
                int j = i + s;

                LOGGER.info(String.format("i: %s    j: %s", i, j));

                int[] minAndMax = minAndMax(i, j);
                m[i][j] = minAndMax[0];
                M[i][j] = minAndMax[1];
            }
        }

        return M[0][numbers.length - 1];
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        scanner.close();

        parseInput(input);

        int result = parentheses();

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            printMatrix(m);
            printMatrix(M);
        }

        System.out.println(result);
    }
}
