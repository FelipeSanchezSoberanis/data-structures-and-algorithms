import java.io.*;
import java.util.*;

public class TreeOrders {

    public static void main(String[] args) throws IOException {
        new Thread(
                        null,
                        new Runnable() {
                            public void run() {
                                try {
                                    new TreeOrders().run();
                                } catch (IOException e) {
                                }
                            }
                        },
                        "1",
                        1 << 26)
                .start();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrdersInner tree = new TreeOrdersInner();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
}

class TreeOrdersInner {
    int n;
    int[] key, left, right;

    void read() throws IOException {
        FastScanner in = new FastScanner();
        n = in.nextInt();
        key = new int[n];
        left = new int[n];
        right = new int[n];
        for (int i = 0; i < n; i++) {
            key[i] = in.nextInt();
            left[i] = in.nextInt();
            right[i] = in.nextInt();
        }
    }

    private Optional<Integer> getLeftChild(int i) {
        if (left[i] == -1) return Optional.empty();
        return Optional.of(left[i]);
    }

    private Optional<Integer> getRightChild(int i) {
        if (right[i] == -1) return Optional.empty();
        return Optional.of(right[i]);
    }

    private int getKey(int i) {
        return key[i];
    }

    private void inOrderRecursive(int i, ArrayList<Integer> result) {
        Optional<Integer> leftChild = getLeftChild(i);
        if (leftChild.isPresent()) inOrderRecursive(leftChild.get(), result);

        result.add(getKey(i));

        Optional<Integer> rightChild = getRightChild(i);
        if (rightChild.isPresent()) inOrderRecursive(rightChild.get(), result);
    }

    private void preOrderRecursive(int i, ArrayList<Integer> result) {
        result.add(getKey(i));

        Optional<Integer> leftChild = getLeftChild(i);
        if (leftChild.isPresent()) preOrderRecursive(leftChild.get(), result);

        Optional<Integer> rightChild = getRightChild(i);
        if (rightChild.isPresent()) preOrderRecursive(rightChild.get(), result);
    }

    private void postOrderRecursive(int i, ArrayList<Integer> result) {
        Optional<Integer> leftChild = getLeftChild(i);
        if (leftChild.isPresent()) postOrderRecursive(leftChild.get(), result);

        Optional<Integer> rightChild = getRightChild(i);
        if (rightChild.isPresent()) postOrderRecursive(rightChild.get(), result);

        result.add(getKey(i));
    }

    List<Integer> inOrder() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        inOrderRecursive(0, result);
        return result;
    }

    List<Integer> preOrder() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        preOrderRecursive(0, result);
        return result;
    }

    List<Integer> postOrder() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        postOrderRecursive(0, result);
        return result;
    }
}

class FastScanner {
    StringTokenizer tok = new StringTokenizer("");
    BufferedReader in;

    FastScanner() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() throws IOException {
        while (!tok.hasMoreElements()) tok = new StringTokenizer(in.readLine());
        return tok.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}
