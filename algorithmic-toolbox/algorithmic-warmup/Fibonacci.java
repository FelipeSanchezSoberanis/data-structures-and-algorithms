import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Fibonacci {
    private static BigInteger fibonacci(BigInteger n) {
        if (n.compareTo(new BigInteger("0")) == 0) return new BigInteger("0");

        if (n.compareTo(new BigInteger("2")) == -1 || n.compareTo(new BigInteger("2")) == 0)
            return new BigInteger("1");

        BigInteger[] numbers = new BigInteger[3];

        numbers[0] = new BigInteger("1");
        numbers[1] = new BigInteger("1");

        for (BigInteger i = new BigInteger("2");
                i.compareTo(n) == -1;
                i = i.add(new BigInteger("1"))) {
            numbers[2] = numbers[0].add(numbers[1]);

            numbers[0] = numbers[1];
            numbers[1] = numbers[2];
        }

        return numbers[2];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        BigInteger n = new BigInteger(br.readLine());

        System.out.println(fibonacci(n));
    }
}
