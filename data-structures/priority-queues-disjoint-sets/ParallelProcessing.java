import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ParallelProcessing {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ParallelProcessing().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        CompleteBinaryTree tree = new CompleteBinaryTree(nextFreeTime);
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];

            DoubleValueNode topNode = tree.peekMin();

            assignedWorker[i] = topNode.getOriginalIndex();
            startTime[i] = topNode.getValue();
            tree.changePriority(0, topNode.getValue() + duration);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

class CompleteBinaryTree {
    private DoubleValueNode[] nodes;
    private int size;

    public CompleteBinaryTree(long[] nodes) {
        this.nodes = new DoubleValueNode[nodes.length];
        this.size = nodes.length;

        for (int i = 0; i < this.size; i++) {
            this.nodes[i] = new DoubleValueNode(i, nodes[i]);
        }

        buildHeap();
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

        if (rightIndex < size && nodes[rightIndex].getValue() < nodes[minIndex].getValue()) {
            minIndex = rightIndex;
        } else if (rightIndex < size
                && nodes[rightIndex].getValue() == nodes[minIndex].getValue()
                && nodes[rightIndex].getOriginalIndex() < nodes[minIndex].getOriginalIndex()) {
            minIndex = rightIndex;
        }

        if (leftIndex < size && nodes[leftIndex].getValue() < nodes[minIndex].getValue()) {
            minIndex = leftIndex;
        } else if (leftIndex < size
                && nodes[leftIndex].getValue() == nodes[minIndex].getValue()
                && nodes[leftIndex].getOriginalIndex() < nodes[minIndex].getOriginalIndex()) {
            minIndex = leftIndex;
        }

        if (minIndex != i) {
            swap(i, minIndex);
            siftDown(minIndex);
        }
    }

    public void swap(int i, int j) {
        DoubleValueNode value = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = value;
    }

    public DoubleValueNode extractMin() {
        DoubleValueNode min = nodes[0];

        swap(0, size - 1);
        size--;
        siftDown(0);

        return min;
    }

    public DoubleValueNode peekMin() {
        return nodes[0];
    }

    public void changePriority(int i, long newValue) {
        nodes[i].setValue(newValue);
        siftDown(i);
    }
}

class DoubleValueNode {
    private int originalIndex;
    private long value;

    public DoubleValueNode(int originalIndex, long value) {
        this.originalIndex = originalIndex;
        this.value = value;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
