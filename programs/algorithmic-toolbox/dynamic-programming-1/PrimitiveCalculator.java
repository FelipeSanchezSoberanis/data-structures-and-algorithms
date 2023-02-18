import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimitiveCalculator {
    private static final Logger LOGGER = Logger.getLogger(PrimitiveCalculator.class.getName());

    private static int primitiveCalculator(int number) {
        int[] operations = new int[number + 1];

        for (int i = 0; i < operations.length; i++) {
            if (i == 0 || i == 1) {
                operations[i] = 0;
                continue;
            }

            int option1 = i % 3 == 0 ? operations[i / 3] + 1 : Integer.MAX_VALUE;
            int option2 = i % 2 == 0 ? operations[i / 2] + 1 : Integer.MAX_VALUE;
            int option3 = i - 1 > 0 ? operations[i - 1] + 1 : Integer.MAX_VALUE;

            operations[i] = Collections.min(List.of(option1, option2, option3));

            LOGGER.log(Level.INFO, "i: {0}", i);
            LOGGER.log(Level.INFO, "Option 1: {0}", option1);
            LOGGER.log(Level.INFO, "Option 2: {0}", option2);
            LOGGER.log(Level.INFO, "Option 3: {0}", option3);
            LOGGER.log(Level.INFO, "Min option: {0}", operations[i]);
        }

        return operations[number];
    }

    public static void main(String[] args) {
        LOGGER.setLevel(Level.INFO);

        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        int noOperations = primitiveCalculator(number);

        System.out.println(noOperations);

        scanner.close();
    }
}
