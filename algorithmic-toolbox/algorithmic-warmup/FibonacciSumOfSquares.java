import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class FibonacciSumOfSquares {
    private static BigDecimal lastDigit(BigDecimal number) {
        return number.remainder(new BigDecimal(10));
    }

    private static BigDecimal[] fibonacciTwoLastValues(long n) {
        n++;

        BigDecimal[] temp = {new BigDecimal(1), new BigDecimal(1)};
        if (n <= 2) return temp;

        BigDecimal[] numbers = new BigDecimal[3];

        numbers[0] = new BigDecimal(1);
        numbers[1] = new BigDecimal(1);

        for (int i = 2; i < n; i++) {
            numbers[2] = numbers[0].add(numbers[1]);

            numbers[0] = numbers[1];
            numbers[1] = numbers[2];
        }

        BigDecimal[] result = {numbers[1], numbers[0]};

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long number = Long.parseLong(br.readLine());
        BigDecimal[] numbers = fibonacciTwoLastValues(number);

        BigDecimal result = numbers[0].multiply(numbers[1]);

        System.out.println(lastDigit(result));
    }
}
