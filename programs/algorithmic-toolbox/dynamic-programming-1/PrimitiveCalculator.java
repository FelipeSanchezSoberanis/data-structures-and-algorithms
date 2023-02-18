import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimitiveCalculator {
    private static final Logger LOGGER = Logger.getLogger(PrimitiveCalculator.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    private static int[] primitiveCalculator(int number) {
        int[] operations = new int[number + 1];
        String[] connections = new String[number + 1];

        for (int i = 0; i < operations.length; i++) {
            if (i == 0 || i == 1) {
                operations[i] = 0;
                continue;
            }

            int option1 = i % 3 == 0 ? operations[i / 3] + 1 : Integer.MAX_VALUE;
            int option2 = i % 2 == 0 ? operations[i / 2] + 1 : Integer.MAX_VALUE;
            int option3 = i - 1 > 0 ? operations[i - 1] + 1 : Integer.MAX_VALUE;

            if (option1 < option2 && option1 < option3) {
                operations[i] = option1;
                connections[i] = "x3";
            } else if (option2 < option3) {
                operations[i] = option2;
                connections[i] = "x2";
            } else {
                operations[i] = option3;
                connections[i] = "+1";
            }

            LOGGER.log(Level.INFO, "i: {0}", i);
            LOGGER.log(Level.INFO, "Option 1: {0}", option1);
            LOGGER.log(Level.INFO, "Option 2: {0}", option2);
            LOGGER.log(Level.INFO, "Option 3: {0}", option3);
            LOGGER.log(Level.INFO, "Min option: {0}", operations[i]);
        }

        int noOperations = operations[number];
        int[] numbers = new int[noOperations + 1];
        int currentNumber = number;
        int counter = noOperations - 1;

        numbers[noOperations] = currentNumber;

        while (currentNumber != 1) {
            String connection = connections[currentNumber];

            switch (connection) {
                case "x3":
                    currentNumber /= 3;
                    break;
                case "x2":
                    currentNumber /= 2;
                    break;
                case "+1":
                    currentNumber--;
                    break;
            }

            numbers[counter] = currentNumber;
            counter--;
        }

        return numbers;
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        int[] operations = primitiveCalculator(number);

        System.out.println(operations.length - 1);
        Arrays.stream(operations).forEach(o -> System.out.format("%s ", o));

        scanner.close();
    }
}
