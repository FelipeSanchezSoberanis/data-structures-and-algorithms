import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciLastDigit {
    private static int lastDigit(int n) {
        return n % 10;
    }

    private static long fibonacciLastDigit(long n) {
        if (n <= 1) return n;

        int[] numbers = new int[3];
        numbers[0] = 0;
        numbers[1] = 1;

        for (long i = 1; i < n; i++) {
            numbers[2] = lastDigit(numbers[0] + numbers[1]);

            numbers[0] = numbers[1];
            numbers[1] = numbers[2];
        }

        return numbers[2];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long n = Long.parseLong(br.readLine());

        System.out.println(fibonacciLastDigit(n));
    }
}
