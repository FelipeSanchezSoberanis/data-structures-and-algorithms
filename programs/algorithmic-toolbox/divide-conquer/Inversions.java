import java.util.Arrays;
import java.util.Scanner;

public class Inversions {
    private static int[] merge(int[] array1, int[] array2) {
        int[] mergedArray = new int[array1.length + array2.length];

        int counter = 0;
        while (array1.length > 0 && array2.length > 0) {
            int numberFromArray1 = array1[0];
            int numberFromArray2 = array2[0];

            if (numberFromArray1 <= numberFromArray2) {
                mergedArray[counter] = numberFromArray1;
                array1 = Arrays.stream(array1).filter(n -> n != numberFromArray1).toArray();
            } else {
                mergedArray[counter] = numberFromArray2;
                array2 = Arrays.stream(array2).filter(n -> n != numberFromArray2).toArray();
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

        int[] B = mergeSort(Arrays.copyOfRange(numbers, 0, m));

        int[] C = mergeSort(Arrays.copyOfRange(numbers, m, numbers.length));

        int[] A = merge(B, C);

        return A;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        Arrays.stream(mergeSort(numbers)).forEach(n -> System.out.format("%s, ", n));
    }
}
