import java.util.Arrays;
import java.util.Scanner;

public class BinarySearchDuplicates {
    private static int binarySearchDuplicates(int[] k, int q) {
        int minIndex = 0;
        int maxIndex = k.length - 1;

        while (maxIndex >= minIndex) {
            int midIndex = (int) Math.floor((minIndex + maxIndex) / 2);

            if (k[midIndex] == q && (midIndex == 0 || k[midIndex - 1] != q)) return midIndex;
            if (k[midIndex] < q) {
                minIndex = midIndex + 1;
            } else {
                maxIndex = midIndex - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        int noQueries = scanner.nextInt();
        int[] queries = new int[noQueries];

        for (int i = 0; i < noQueries; i++) {
            queries[i] = scanner.nextInt();
        }

        scanner.close();

        Arrays.stream(queries)
                .forEach(
                        q -> {
                            System.out.print(binarySearchDuplicates(numbers, q) + " ");
                        });
    }
}
