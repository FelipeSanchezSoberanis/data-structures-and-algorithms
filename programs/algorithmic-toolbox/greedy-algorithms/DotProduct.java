import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class DotProduct {
    private static void printData(int n, int[] prices, int[] clicks) {
        System.out.println();
        System.out.format("n: %s \n", n);

        System.out.print("prices: ");
        Arrays.stream(prices).forEach(price -> System.out.format("%s ", price));
        System.out.println();

        System.out.print("clicks: ");
        Arrays.stream(clicks).forEach(click -> System.out.format("%s ", click));
        System.out.println();
    }

    private static BigInteger maxDotProduct(int[] prices, int[] clicks) {
        Arrays.sort(prices);
        Arrays.sort(clicks);

        BigInteger sum = new BigInteger("0");

        for (int i = 0; i < prices.length; i++) {
            sum =
                    sum.add(
                            new BigInteger(String.valueOf(prices[i]))
                                    .multiply(new BigInteger(String.valueOf(clicks[i]))));
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] prices = new int[n];
        int[] clicks = new int[n];

        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            clicks[i] = scanner.nextInt();
        }

        scanner.close();

        System.out.println(maxDotProduct(prices, clicks));
    }
}
