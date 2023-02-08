import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MajorityElement {
    private static void printData(int noNumbers, int[] numbers) {
        System.out.println("Number of numbers: " + noNumbers);
        Arrays.stream(numbers).forEach(n -> System.out.format("%s, ", n));
    }

    private static boolean majorityElementExists(int[] numbers) {
        Map<Integer, Integer> numbersCount = new HashMap<>();

        for (int number : numbers) {
            int value = numbersCount.get(number) == null ? 1 : numbersCount.get(number) + 1;

            if (value > numbers.length / 2) {
                return true;
            }

            numbersCount.put(number, value);
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        System.out.println(majorityElementExists(numbers) ? 1 : 0);
    }
}
