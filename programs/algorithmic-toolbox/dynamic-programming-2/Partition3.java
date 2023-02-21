import java.util.Arrays;
import java.util.Scanner;

public class Partition3 {
    private static int target;
    private static int[] numbers;
    private static boolean[] used;

    private static boolean backtrack(int i, int k, int subsetSum) {
        if (k == 0) return true;

        if (subsetSum == target) return backtrack(0, k - 1, 0);

        for (int j = i; j < numbers.length; j++) {
            if (used[j] || subsetSum + numbers[j] > target) continue;

            used[j] = true;
            if (backtrack(j + 1, k, subsetSum + numbers[j])) return true;
            used[j] = false;
        }

        return false;
    }

    private static boolean isDivisibleInKSubsets(int[] numbers, int k) {
        if (Arrays.stream(numbers).sum() % k != 0) return false;

        target = Arrays.stream(numbers).sum() / k;

        used = new boolean[numbers.length];
        for (int i = 0; i < used.length; i++) {
            used[i] = false;
        }

        return backtrack(0, k, 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        numbers = new int[noNumbers];
        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        boolean possible = isDivisibleInKSubsets(numbers, 3);

        System.out.println(possible ? 1 : 0);
    }
}
