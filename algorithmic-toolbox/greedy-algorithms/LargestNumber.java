import java.util.Arrays;
import java.util.Scanner;

public class LargestNumber {
    private static boolean isBetter(int number, int maxNumber) {
        if (maxNumber == -1) return true;
        return concatenateInts(number, maxNumber) > concatenateInts(maxNumber, number);
    }

    private static int[] removeFirstByValue(int value, int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == value) {
                numbers[i] = -1;
                break;
            }
        }

        return Arrays.stream(numbers).filter(n -> n != -1).toArray();
    }

    private static int concatenateInts(int a, int b) {
        String s1 = Integer.toString(a);
        String s2 = Integer.toString(b);

        return Integer.parseInt(s1 + s2);
    }

    private static String largestConcatenate(int[] numbers) {
        String yourSalary = new String();

        while (numbers.length > 0) {
            int maxNumber = -1;

            for (int number : numbers) {
                if (isBetter(number, maxNumber)) {
                    maxNumber = number;
                }
            }

            yourSalary = yourSalary.concat(String.valueOf(maxNumber));
            numbers = removeFirstByValue(maxNumber, numbers);
        }

        return yourSalary;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        System.out.println(largestConcatenate(numbers));
    }
}
