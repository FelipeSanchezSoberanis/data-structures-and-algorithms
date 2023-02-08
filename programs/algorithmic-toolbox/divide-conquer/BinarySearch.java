import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {
    private static void printData(int noNumbers, int[] numbers, int noQueries, int[] queries) {
        System.out.println("Numbers of numbers: " + noNumbers);
        Arrays.stream(numbers).forEach(n -> System.out.format("%s, ", n));
        System.out.println();
        System.out.println("Numbers of queries: " + noQueries);
        Arrays.stream(queries).forEach(q -> System.out.format("%s, ", q));
    }

    private static int binarySearch(int[] k, int q) {
        int minIndex = 0;
        int maxIndex = k.length - 1;

        while (maxIndex >= minIndex) {
            int midIndex = (int) Math.floor((minIndex + maxIndex) / 2);

            if (k[midIndex] == q) return midIndex;
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
                            System.out.print(binarySearch(numbers, q) + " ");
                        });
    }
}
