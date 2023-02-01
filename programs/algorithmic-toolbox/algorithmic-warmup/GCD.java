import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GCD {
    private static int euclidGCD(int a, int b) {
        if (b == 0) return a;
        int aPrime = a % b;
        return euclidGCD(b, aPrime);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] numbersAsStrings = br.readLine().split(" +");
        int[] numbers = Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();

        System.out.println(euclidGCD(numbers[0], numbers[1]));
    }
}
