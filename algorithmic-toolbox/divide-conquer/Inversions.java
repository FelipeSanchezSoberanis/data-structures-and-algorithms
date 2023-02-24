import java.util.Arrays;
import java.util.Scanner;

public class Inversions {
    private static int noInversions = 0;

    private static int[] merge(int[] array1, int[] array2) {
        int[] mergedArray = new int[array1.length + array2.length];

        int array1OriginalLength = array1.length;

        int counter = 0;
        int inversionCounterAdjustment = 0;
        while (array1.length > 0 && array2.length > 0) {
            if (array1[0] <= array2[0]) {
                mergedArray[counter] = array1[0];
                array1 = Arrays.copyOfRange(array1, 1, array1.length);

                inversionCounterAdjustment++;
            } else {
                mergedArray[counter] = array2[0];
                array2 = Arrays.copyOfRange(array2, 1, array2.length);

                noInversions += array1OriginalLength - inversionCounterAdjustment;
            }

            counter++;
        }

        for (int number : array1) {
            mergedArray[counter] = number;
            counter++;
        }

        for (int number : array2) {
            mergedArray[counter] = number;
            counter++;
        }

        return mergedArray;
    }

    private static int[] mergeSort(int[] numbers) {
        if (numbers.length == 1) return numbers;

        int m = Math.floorDiv(numbers.length, 2);

        int[] b = mergeSort(Arrays.copyOfRange(numbers, 0, m));
        int[] c = mergeSort(Arrays.copyOfRange(numbers, m, numbers.length));
        int[] a = merge(b, c);

        return a;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        mergeSort(numbers);

        System.out.println(noInversions);
    }
}
