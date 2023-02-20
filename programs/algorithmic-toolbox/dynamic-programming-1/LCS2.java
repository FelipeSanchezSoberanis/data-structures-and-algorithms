import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LCS2 {
    private static final Logger LOGGER = Logger.getLogger(LCS2.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    private static int getNoAlignedNumbers(
            List<String> alignedString1, List<String> alignedString2) {
        int result = 0;
        for (int i = 0; i < alignedString1.size(); i++) {
            if (alignedString1.get(i).equals(alignedString2.get(i))) result++;
        }
        return result;
    }

    private static List<List<String>> getAlignmentFromMatrix(
            int[][] distanceMatrix, int[] A, int[] B) {
        List<String> alignedString1 = new ArrayList<String>();
        List<String> alignedString2 = new ArrayList<String>();

        outputAlignment(
                A,
                B,
                distanceMatrix,
                distanceMatrix.length - 1,
                distanceMatrix[distanceMatrix.length - 1].length - 1,
                alignedString1,
                alignedString2);

        List<List<String>> alignedStrings = new ArrayList<>();
        alignedStrings.add(alignedString1);
        alignedStrings.add(alignedString2);
        return alignedStrings;
    }

    private static void outputAlignment(
            int[] A,
            int[] B,
            int[][] D,
            int i,
            int j,
            List<String> alignedString1,
            List<String> alignedString2) {
        if (i == 0 && j == 0) return;

        if (i > 0 && D[i][j] == D[i - 1][j] + 1) {
            outputAlignment(A, B, D, i - 1, j, alignedString1, alignedString2);

            alignedString1.add(Integer.toString(A[i - 1]));
            alignedString2.add("-");

        } else if (j > 0 && D[i][j] == D[i][j - 1] + 1) {
            outputAlignment(A, B, D, i, j - 1, alignedString1, alignedString2);

            alignedString1.add("-");
            alignedString2.add(Integer.toString(B[j - 1]));
        } else {
            outputAlignment(A, B, D, i - 1, j - 1, alignedString1, alignedString2);

            alignedString1.add(Integer.toString(A[i - 1]));
            alignedString2.add(Integer.toString(B[j - 1]));
        }

        LOGGER.info(
                String.format(
                        "String1: %s    String2: %s",
                        alignedString1.toString(), alignedString2.toString()));
    }

    private static int[][] editDistanceMatrix(int[] A, int[] B) {
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

        return D;
    }

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

        int[][] distanceMatrix = editDistanceMatrix(numberArray1, numberArray2);
        List<List<String>> alignedStrings =
                getAlignmentFromMatrix(distanceMatrix, numberArray1, numberArray2);
        List<String> alignedString1 = alignedStrings.get(0);
        List<String> alignedString2 = alignedStrings.get(1);
        int result = getNoAlignedNumbers(alignedString1, alignedString2);

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            System.out.println("=====");
            System.out.println("Aligned words:");
            for (String c : alignedString1) {
                System.out.format("%s ", c);
            }
            System.out.println();
            for (String c : alignedString2) {
                System.out.format("%s ", c);
            }
            System.out.println();
        }

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            System.out.print("=====\nResult: ");
        }
        System.out.println(result);
    }
}
