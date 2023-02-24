import java.util.Scanner;

public class ChangeDp {
    private static int dpChange(int money, int[] coinDenominations) {
        int[] minNumCoins = new int[money + 1];

        for (int m = 1; m <= money; m++) {
            minNumCoins[m] = Integer.MAX_VALUE;

            for (int i = 0; i < coinDenominations.length; i++) {
                if (m >= coinDenominations[i]) {
                    int numCoins = minNumCoins[m - coinDenominations[i]] + 1;

                    if (numCoins < minNumCoins[m]) {
                        minNumCoins[m] = numCoins;
                    }
                }
            }
        }

        return minNumCoins[money];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int money = scanner.nextInt();
        int[] coinDenominations = {1, 3, 4};

        scanner.close();

        int minNoCoins = dpChange(money, coinDenominations);

        System.out.println(minNoCoins);
    }
}
