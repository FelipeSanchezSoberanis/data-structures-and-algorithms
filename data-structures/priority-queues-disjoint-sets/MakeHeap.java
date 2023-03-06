import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MakeHeap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noNumbers = scanner.nextInt();
        int[] numbers = new int[noNumbers];

        for (int i = 0; i < noNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        CompleteBinaryTree tree = new CompleteBinaryTree(numbers);

        List<Swap> swaps = tree.getSwaps();

        System.out.println(swaps.size());
        swaps.forEach(s -> System.out.format("%s %s \n", s.getFirstNumber(), s.getSecondNumber()));
    }
}

class CompleteBinaryTree {
    private int[] nodes;
    private List<Swap> swaps;

    public CompleteBinaryTree(int[] nodes) {
        this.nodes = nodes.clone();
        this.swaps = new ArrayList<>();

        buildHeap();
    }

    public List<Swap> getSwaps() {
        return this.swaps;
    }

    private void buildHeap() {
        for (int i = Math.floorDiv(nodes.length, 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftDown(int i) {
        int minIndex = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < nodes.length && nodes[leftIndex] < nodes[minIndex]) minIndex = leftIndex;
        if (rightIndex < nodes.length && nodes[rightIndex] < nodes[minIndex]) minIndex = rightIndex;
        if (minIndex != i) {
            swaps.add(new Swap(i, minIndex));
            swap(i, minIndex);
            siftDown(minIndex);
        }
    }

    public void swap(int i, int j) {
        int value = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = value;
    }
}

class Swap {
    private int firstNumber;
    private int secondNumber;

    public Swap(int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }
}
