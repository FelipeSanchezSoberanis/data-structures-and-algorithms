import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class HugeFibonacci {
    private static BigInteger fibonacci(long n) {
        BigInteger nBigInteger = new BigInteger(String.valueOf(n));

        if (nBigInteger.compareTo(new BigInteger("2")) == -1) return nBigInteger;

        BigInteger[] numbers = new BigInteger[3];
        numbers[0] = new BigInteger("0");
        numbers[1] = new BigInteger("1");

        for (long i = 1; i < n; i++) {
            numbers[2] = numbers[0].add(numbers[1]);

            numbers[0] = numbers[1];
            numbers[1] = numbers[2];
        }

        return numbers[2];
    }

    private static BigInteger fibonacciModulus(long n, long m) {
        return fibonacci(n).remainder(new BigInteger(String.valueOf(m)));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] numbersAsString = br.readLine().split(" +");

        long n = Long.parseLong(numbersAsString[0]);
        long m = Long.parseLong(numbersAsString[1]);

        System.out.println(fibonacciModulus(n, m));
    }
}
