import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sorting {
    private static int[] randomizedQuickSort(int[] numbers) {
        if (numbers.length <= 1) return numbers;

        Random random = new Random();
        int pivot = numbers[random.nextInt(numbers.length)];

        int[] smaller = Arrays.stream(numbers).filter(n -> n < pivot).toArray();
        int[] equal = Arrays.stream(numbers).filter(n -> n == pivot).toArray();
        int[] larger = Arrays.stream(numbers).filter(n -> n > pivot).toArray();

        int[] smallerSorted = randomizedQuickSort(smaller);
        int[] largerSorted = randomizedQuickSort(larger);

        int[] sortedArray = new int[numbers.length];

        int counter = 0;
        for (int n : smallerSorted) {
            sortedArray[counter] = n;
            counter++;
        }

        for (int n : equal) {
            sortedArray[counter] = n;
            counter++;
        }

        for (int n : largerSorted) {
            sortedArray[counter] = n;
            counter++;
        }

        return sortedArray;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        int[] result = randomizedQuickSort(numbers);

        Arrays.stream(result).forEach(n -> System.out.format("%s ", n));
    }
}
