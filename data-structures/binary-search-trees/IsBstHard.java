import java.io.*;
import java.util.*;

public class IsBstHard {
    public static void main(String[] args) throws IOException {
        new Thread(
                        null,
                        new Runnable() {
                            public void run() {
                                try {
                                    new IsBstHard().run();
                                } catch (IOException e) {
                                }
                            }
                        },
                        "1",
                        1 << 26)
                .start();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}

class FastScanner {
    private StringTokenizer tok = new StringTokenizer("");
    private BufferedReader in;

    public FastScanner() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    private String next() throws IOException {
        while (!tok.hasMoreElements()) tok = new StringTokenizer(in.readLine());
        return tok.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}

class IsBST {
    private int nodes;
    private Node[] tree;

    public void read() throws IOException {
        FastScanner in = new FastScanner();
        nodes = in.nextInt();
        tree = new Node[nodes];
        for (int i = 0; i < nodes; i++) {
            tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
        }
    }

    private boolean isBinarySearchTreeRecursive(int i, int min, int max) {
        if (i < 0 || i >= tree.length) return true;

        Node node = tree[i];

        if (node.getKey() == -1) return true;

        if (node.getKey() < min || node.getKey() > max) return false;

        return isBinarySearchTreeRecursive(node.getLeft(), min, node.getKey() - 1)
                && isBinarySearchTreeRecursive(node.getRight(), node.getKey() - 1, max);
    }

    public boolean isBinarySearchTree() {
        return isBinarySearchTreeRecursive(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}

class Node {
    private int key;
    private int left;
    private int right;

    Node(int key, int left, int right) {
        this.left = left;
        this.right = right;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
