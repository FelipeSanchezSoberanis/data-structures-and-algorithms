import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LCM {
    private static long euclidLCM(long a, long b) {
        return (a * b) / (euclidGCD(a, b));
    }

    private static long euclidGCD(long a, long b) {
        if (b == 0) return a;
        long aPrime = a % b;
        return euclidGCD(b, aPrime);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] numbersAsStrings = br.readLine().split(" +");
        long[] numbers = Arrays.stream(numbersAsStrings).mapToLong(Long::parseLong).toArray();

        System.out.println(euclidLCM(numbers[0], numbers[1]));
    }
}
