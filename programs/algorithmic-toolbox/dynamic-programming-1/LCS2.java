import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LCS2 {
    private static final Logger LOGGER = Logger.getLogger(LCS2.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static String[] getAlignmentFromMatrix(
            int[][] distanceMatrix, String string1, String string2) {
        char[] A = string1.toCharArray();
        char[] B = string2.toCharArray();

        StringBuilder alignedStringBuilder1 = new StringBuilder();
        StringBuilder alignedStringBuilder2 = new StringBuilder();

        outputAlignment(
                A,
                B,
                distanceMatrix,
                distanceMatrix.length - 1,
                distanceMatrix[distanceMatrix.length - 1].length - 1,
                alignedStringBuilder1,
                alignedStringBuilder2);

        String[] alignedStrings = {
            alignedStringBuilder1.toString(), alignedStringBuilder2.toString()
        };
        return alignedStrings;
    }

    private static void outputAlignment(
            char[] A,
            char[] B,
            int[][] D,
            int i,
            int j,
            StringBuilder alignedString1,
            StringBuilder alignedString2) {
        if (i == 0 && j == 0) return;

        if (i > 0 && D[i][j] == D[i - 1][j] + 1) {
            outputAlignment(A, B, D, i - 1, j, alignedString1, alignedString2);

            alignedString1.append(A[i - 1]);
            alignedString2.append("-");

        } else if (j > 0 && D[i][j] == D[i][j - 1] + 1) {
            outputAlignment(A, B, D, i, j - 1, alignedString1, alignedString2);

            alignedString1.append("-");
            alignedString2.append(B[j - 1]);
        } else {
            outputAlignment(A, B, D, i - 1, j - 1, alignedString1, alignedString2);

            alignedString1.append(A[i - 1]);
            alignedString2.append(B[j - 1]);
        }

        LOGGER.info(
                String.format(
                        "String1: %s    String2: %s",
                        alignedString1.toString(), alignedString2.toString()));
    }

    private static int[][] editDistanceMatrix(String string1, String string2) {
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

        return D;
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        String string1 = scanner.nextLine();
        String string2 = scanner.nextLine();

        scanner.close();

        int[][] distanceMatrix = editDistanceMatrix(string1, string2);
        String[] alignedStrings = getAlignmentFromMatrix(distanceMatrix, string1, string2);
        String alignedString1 = alignedStrings[0];
        String alignedString2 = alignedStrings[1];
        int result = getNoAlignedChars(alignedString1, alignedString2);

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            System.out.println("=====");
            System.out.println("Aligned words:");
            for (char c : alignedString1.toCharArray()) {
                System.out.format("%s ", c);
            }
            System.out.println();
            for (char c : alignedString2.toCharArray()) {
                System.out.format("%s ", c);
            }
            System.out.println();
        }

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            System.out.print("=====\nResult: ");
        }
        System.out.println(result);
    }

    private static int getNoAlignedChars(String alignedString1, String alignedString2) {
        int result = 0;
        for (int i = 0; i < alignedString1.length(); i++) {
            if (alignedString1.charAt(i) == alignedString2.charAt(i)) result++;
        }
        return result;
    }
}
