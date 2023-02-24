import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DifferentSummands {
    private static List<Integer> getUniqueSummands(int n) {
        List<Integer> summands = new LinkedList<>();

        int i = 1;
        int sum = 0;

        while (sum < n) {
            if ((sum + i) > n) {
                summands.add(n - sum);

                int lastSummand = summands.get(summands.size() - 1);
                int secondToLastSummand = summands.get(summands.size() - 2);
                if (lastSummand <= secondToLastSummand) {
                    summands.set(summands.size() - 2, lastSummand + secondToLastSummand);
                    summands.remove(summands.size() - 1);
                }
            } else {
                summands.add(i);
            }

            sum += i;
            i++;
        }

        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        scanner.close();

        List<Integer> summands = getUniqueSummands(n);

        System.out.println(summands.size());
        summands.forEach(s -> System.out.format("%s ", s));
    }
}
