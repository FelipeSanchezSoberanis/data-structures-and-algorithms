import java.util.Scanner;

public class CarFueling {
    private static int[] removeByIndex(int[] numbers, int index) {
        int[] res = new int[numbers.length - 1];

        int counter = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (i == index) continue;

            res[counter] = numbers[i];
            counter++;
        }

        return res;
    }

    private static int refills(int location, int[] stops, int m, int d) {
        if (location + m >= d) return 0;

        if (stops.length == 0 || (stops[0] - location) > m) throw new RuntimeException();

        int lastStop = location;

        while (stops.length > 0 && (stops[0] - location) <= m) {
            lastStop = stops[0];
            stops = removeByIndex(stops, 0);
        }

        try {
            return 1 + refills(lastStop, stops, m, d);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int d = scanner.nextInt();
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[] stops = new int[n];

        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        scanner.close();

        try {
            System.out.println(refills(0, stops, m, d));
        } catch (Exception e) {
            System.out.println(-1);
        }
    }
}
