import java.util.Arrays;
import java.util.Scanner;

public class DifferentSummands {

    private static int[] getUniqueSummands(int n) {
        if (n == 1 || n == 2) {
            int[] summands = {n};
            return summands;
        }

        int maxNumberSummands = n / 2 + 1;

        int[] summands = new int[n];
        summands[0] = 1;

        int sum = 1;

        for (int i = 1; i < maxNumberSummands; i++) {
            int nextNumber = i + 1;

            if (sum + nextNumber > n) {
                summands[i] = n - sum;

                if (summands[i] <= summands[i - 1]) {
                    summands[i - 1] += summands[i];
                    summands[i] = 0;
                }

                return Arrays.stream(summands).filter(s -> s != 0).toArray();
            }

            summands[i] = nextNumber;
            sum += nextNumber;

            if (sum == n) return Arrays.stream(summands).filter(s -> s != 0).toArray();
        }

        return Arrays.stream(summands).filter(s -> s != 0).toArray();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        scanner.close();

        int[] summands = getUniqueSummands(n);

        System.out.println(summands.length);
        Arrays.stream(summands).forEach(s -> System.out.format("%s ", s));
    }
}
