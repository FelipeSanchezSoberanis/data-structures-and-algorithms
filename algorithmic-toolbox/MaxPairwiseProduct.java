import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MaxPairwiseProduct {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int noNumbers = Integer.parseInt(br.readLine());
        String[] numbersAsStrings = br.readLine().split(" +");
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = Integer.parseInt(numbersAsStrings[i]);
        }

        Arrays.sort(numbers);

        Long maxNumber1 = Long.valueOf(numbers[noNumbers - 1]);
        Long maxNumber2 = Long.valueOf(numbers[noNumbers - 2]);

        System.out.println(maxNumber1 * maxNumber2);
    }
}
