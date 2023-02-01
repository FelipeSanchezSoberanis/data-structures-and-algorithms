import java.util.Scanner;

public class Change {
    private static int change(int money) {
        int numCoins = 0;

        while (money > 0) {
            if (money >= 10) {
                money -= 10;
            } else if (money >= 5) {
                money -= 5;
            } else {
                money -= 1;
            }

            numCoins++;
        }

        return numCoins;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int money = scanner.nextInt();

        System.out.println(change(money));

        scanner.close();
    }
}
