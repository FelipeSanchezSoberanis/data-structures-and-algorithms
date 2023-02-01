import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FibonacciPartial {
    private static long lastDigit(long number) {
        return number % 10;
    }

    private static long fibonacciPartialSumLastDigit(int m, int n) {
        List<Long> numbers = new ArrayList<>();

        numbers.add(1L);
        numbers.add(1L);

        for (int i = 2; i < n; i++) {
            numbers.add(lastDigit(numbers.get(i - 1) + numbers.get(i - 2)));
        }

        List<Long> numbersInRange = numbers.subList(m - 1, n);

        return lastDigit(numbersInRange.stream().reduce(0L, Long::sum));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] numbersAsString = br.readLine().split(" +");

        int m = Integer.parseInt(numbersAsString[0]);
        int n = Integer.parseInt(numbersAsString[1]);

        System.out.println(fibonacciPartialSumLastDigit(m, n));
    }
}
