import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Knapsack {
    private static final Logger LOGGER = Logger.getLogger(Knapsack.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    private static void printMatrix(int[][] matrix) {
        System.out.println();
        System.out.println("Matrix:");

        for (int[] row : matrix) {
            for (int rowItem : row) {
                System.out.format("[%2d] ", rowItem);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }

    private static int knapsack(int W, int[] w, int[] v) {
        int[][] value = new int[W + 1][w.length + 1];

        for (int i = 0; i < W + 1; i++) {
            value[i][0] = 0;
        }
        for (int i = 0; i < w.length + 1; i++) {
            value[0][i] = 0;
        }

        for (int i = 1; i < w.length + 1; i++) {
            for (int j = 1; j < W + 1; j++) {
                value[j][i] = value[j][i - 1];

                if (w[i - 1] <= j) {
                    int val = value[j - w[i - 1]][i - 1] + v[i - 1];

                    if (value[j][i] < val) value[j][i] = val;
                }
            }
        }

        if (LOGGER_LEVEL.equals(Level.INFO)) printMatrix(value);

        return value[W][w.length];
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        int maxWeight = scanner.nextInt();
        int noWeights = scanner.nextInt();
        int[] weights = new int[noWeights];
        // int[] values = new int[noWeights];

        for (int i = 0; i < noWeights; i++) {
            weights[i] = scanner.nextInt();
        }
        // for (int i = 0; i < noWeights; i++) {
        //     values[i] = scanner.nextInt();
        // }

        scanner.close();

        // int result = knapsack(maxWeight, weights, values);
        int result = knapsack(maxWeight, weights, weights);

        System.out.println(result);
    }
}
