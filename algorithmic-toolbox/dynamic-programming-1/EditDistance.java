import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditDistance {
    private static final Logger LOGGER = Logger.getLogger(EditDistance.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    private static int editDistance(String string1, String string2) {
        char[] A = string1.toCharArray();
        char[] B = string2.toCharArray();

        int ALength = A.length + 1;
        int BLength = B.length + 1;

        int[][] D = new int[ALength][BLength];

        for (int i = 0; i < ALength; i++) {
            for (int j = 0; j < BLength; j++) {
                LOGGER.info(String.format("i: %s    j: %s", i, j));

                if (j == 0) {
                    D[i][0] = i;
                    continue;
                } else if (i == 0) {
                    D[0][j] = j;
                    continue;
                }

                int insertion = D[i][j - 1] + 1;
                int deletion = D[i - 1][j] + 1;

                if (A[i - 1] == B[j - 1]) {
                    int match = D[i - 1][j - 1];
                    int[] options = {insertion, deletion, match};
                    D[i][j] = Arrays.stream(options).min().getAsInt();

                    LOGGER.info(String.format("options: %s", Arrays.toString(options)));
                    LOGGER.info(String.format("min: %s", D[i][j]));
                } else {
                    int mismatch = D[i - 1][j - 1] + 1;
                    int[] options = {insertion, deletion, mismatch};
                    D[i][j] = Arrays.stream(options).min().getAsInt();

                    LOGGER.info(String.format("options: %s", Arrays.toString(options)));
                    LOGGER.info(String.format("min: %s", D[i][j]));
                }
            }
        }

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            System.out.println("=====");
            System.out.println("Matrix:");
            for (int i = 0; i < ALength; i++) {
                for (int j = 0; j < BLength; j++) {
                    System.out.format("[%s]", D[i][j]);
                }
                System.out.println();
            }
        }

        return D[ALength - 1][BLength - 1];
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        String string1 = scanner.nextLine();
        String string2 = scanner.nextLine();

        scanner.close();

        int result = editDistance(string1, string2);

        if (LOGGER_LEVEL.equals(Level.INFO)) System.out.print("=====\nResult: ");
        System.out.println(result);
    }
}
