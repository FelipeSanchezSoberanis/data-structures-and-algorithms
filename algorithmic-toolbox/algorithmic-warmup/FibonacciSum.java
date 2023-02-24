import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciSum {
    private static long lastDigit(long number) {
        return number % 10;
    }

    private static int lastDigit(int number) {
        return number % 10;
    }

    private static long fibonacciSumLastDigit(long n) {
        if (n < 2) return n;

        int[] numbers = new int[3];

        numbers[0] = 0;
        numbers[1] = 1;

        for (long i = -1; i < n; i++) {
            numbers[2] = numbers[0] + numbers[1];

            numbers[0] = numbers[1];
            numbers[1] = numbers[2];
        }

        return lastDigit(numbers[2] - 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long number = Long.parseLong(br.readLine());

        System.out.println(fibonacciSumLastDigit(number));
    }
}
