import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LCS2 {
    private static final Logger LOGGER = Logger.getLogger(LCS2.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        int m = scanner.nextInt();
        int[] numberArray1 = new int[m];
        for (int i = 0; i < m; i++) {
            numberArray1[i] = scanner.nextInt();
        }

        int n = scanner.nextInt();
        int[] numberArray2 = new int[n];
        for (int i = 0; i < n; i++) {
            numberArray2[i] = scanner.nextInt();
        }

        scanner.close();

        int result = lcs(numberArray1, numberArray2);

        System.out.println(result);
    }

    private static int lcs(int[] numberArray1, int[] numberArray2) {
        int matrix[][] = new int[numberArray1.length + 1][numberArray2.length + 1];

        for (int i = 0; i <= numberArray1.length; i++) {
            matrix[i][numberArray2.length] = 0;
        }
        for (int i = 0; i <= numberArray2.length; i++) {
            matrix[numberArray1.length][i] = 0;
        }

        for (int i = numberArray1.length - 1; i >= 0; i--) {
            for (int j = numberArray2.length - 1; j >= 0; j--) {
                if (numberArray1[i] == numberArray2[j]) {
                    matrix[i][j] = matrix[i + 1][j + 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j + 1], matrix[i + 1][j]);
                }
            }
        }

        return matrix[0][0];
    }
}
