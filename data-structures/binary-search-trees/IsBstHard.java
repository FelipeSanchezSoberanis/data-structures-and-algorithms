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

    public IsBST() {}

    public void read() throws IOException {
        FastScanner in = new FastScanner();
        nodes = in.nextInt();
        tree = new Node[nodes];
        for (int i = 0; i < nodes; i++) {
            tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
        }
    }

    private Optional<Node> getNode(int i) {
        return (i < 0 || i >= tree.length) ? Optional.empty() : Optional.of(tree[i]);
    }

    public boolean isBinarySearchTree() {
        if (tree.length == 0 || tree.length == 1) return true;

        Stack<Integer> minStack = new Stack<>();
        Stack<Node> nodeStack = new Stack<>();
        Stack<Integer> maxStack = new Stack<>();

        minStack.push(Integer.MIN_VALUE);
        nodeStack.push(tree[0]);
        maxStack.push(Integer.MAX_VALUE);

        while (nodeStack.size() > 0) {
            Integer min = minStack.pop();
            Node rootNode = nodeStack.pop();
            Integer max = maxStack.pop();

            if (rootNode.getKey() < min || rootNode.getKey() >= max) {
                return false;
            }

            Optional<Node> leftChild = getNode(rootNode.getLeft());
            Optional<Node> rightChild = getNode(rootNode.getRight());
            if (leftChild.isPresent()) {
                minStack.push(min);
                nodeStack.push(leftChild.get());
                maxStack.push(rootNode.getKey());
            }
            if (rightChild.isPresent()) {
                minStack.push(rootNode.getKey());
                nodeStack.push(rightChild.get());
                maxStack.push(max);
            }
        }

        return true;
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
