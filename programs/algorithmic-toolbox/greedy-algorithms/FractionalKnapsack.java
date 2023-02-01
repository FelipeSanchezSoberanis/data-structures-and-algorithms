import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {
    private static void printData(int noCompounds, int capacity, int[] costs, int[] weights) {
        System.out.format("Number of compounds: %s \n", noCompounds);
        System.out.format("Backpack capacity: %s \n", capacity);
        System.out.print("Costs: ");
        Arrays.stream(costs).forEach(c -> System.out.printf("%s ", c));
        System.out.println();
        System.out.print("Weights: ");
        Arrays.stream(weights).forEach(w -> System.out.printf("%s ", w));
        System.out.println();
    }

    private static int getIndexOfMaxValueByWeight(int[] weights, int[] costs) {
        double[] costsByWeight = new double[weights.length];

        for (int i = 0; i < costsByWeight.length; i++) {
            costsByWeight[i] = ((double) costs[i] / weights[i]);
        }

        int res = 0;
        double maxValue = costsByWeight[0];

        for (int i = 0; i < costsByWeight.length; i++) {
            double currentValue = costsByWeight[i];

            if (currentValue > maxValue) {
                maxValue = currentValue;
                res = i;
            }
        }

        return res;
    }

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

    private static double maximumLoot(int capacity, int[] weights, int[] costs) {
        if (capacity == 0 || weights.length == 0) return 0;

        int m = getIndexOfMaxValueByWeight(weights, costs);
        int amount = Math.min(weights[m], capacity);
        double value = costs[m] * ((double) amount / weights[m]);

        int[] updatedWeights = removeByIndex(weights, m);
        int[] updatedCosts = removeByIndex(costs, m);
        int updatedCapacity = capacity - amount;

        return value + maximumLoot(updatedCapacity, updatedWeights, updatedCosts);
    }

    public static void main(String[] args) {
        int noCompounds, capacity;

        Scanner scanner = new Scanner(System.in);

        noCompounds = scanner.nextInt();
        capacity = scanner.nextInt();

        int[] costs = new int[noCompounds];
        int[] weights = new int[noCompounds];

        for (int i = 0; i < noCompounds; i++) {
            costs[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }

        scanner.close();

        System.out.println(maximumLoot(capacity, weights, costs));
    }
}
