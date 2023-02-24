import java.util.Scanner;

public class CoveringSegments {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long[] leftCoords = new long[n];
        long[] rightCoords = new long[n];

        for (int i = 0; i < n; i++) {
            leftCoords[i] = scanner.nextInt();
            rightCoords[i] = scanner.nextInt();
        }

        scanner.close();
    }
}
