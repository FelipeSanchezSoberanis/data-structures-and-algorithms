import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    class Table {
        Table parent;
        int rank;
        int numberOfRows;

        Table(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }

        Table getParent() {
            if (this != this.parent) this.parent = this.parent.getParent();
            return this.parent;
        }
    }

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();

        if (realDestination == realSource) return;

        if (realDestination.rank > realSource.rank) {
            realSource.parent = realDestination;
            realDestination.numberOfRows += realSource.numberOfRows;

            if (realDestination.numberOfRows > maximumNumberOfRows)
                maximumNumberOfRows = realDestination.numberOfRows;
        } else {
            realDestination.parent = realSource;
            realSource.numberOfRows += realDestination.numberOfRows;
            if (realDestination.rank == realSource.rank) source.rank += 1;

            if (realSource.numberOfRows > maximumNumberOfRows)
                maximumNumberOfRows = realSource.numberOfRows;
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}

class DisjointSets {
    private int[] parent;
    private int[] rank;

    public DisjointSets(int[] values) {
        int maxValue = Arrays.stream(values).max().getAsInt();

        this.parent = new int[maxValue + 1];
        this.rank = new int[maxValue + 1];

        for (int i = 0; i < this.rank.length; i++) {
            this.rank[i] = -1;
        }

        for (int value : values) {
            makeSet(value);
        }
    }

    public void makeSet(int i) {
        parent[i] = i;
        rank[i] = 0;
    }

    public int find(int i) {
        if (i != parent[i]) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    public void union(int i, int j) {
        int iId = find(i);
        int jId = find(j);

        if (iId == jId) return;

        if (rank[iId] > rank[jId]) {
            parent[jId] = iId;
        } else {
            parent[iId] = jId;
            if (rank[iId] == rank[jId]) rank[jId] += 1;
        }
    }

    @Override
    public String toString() {
        List<Integer> parentFiltered = new ArrayList<>();
        List<Integer> rankFiltered = new ArrayList<>();
        List<Integer> valuesFiltered = new ArrayList<>();

        for (int i = 0; i < parent.length; i++) {
            if (rank[i] == -1) continue;

            parentFiltered.add(parent[i]);
            rankFiltered.add(rank[i]);
            valuesFiltered.add(i);
        }

        return "DisjointSets [values="
                + valuesFiltered.toString()
                + ", parent="
                + parentFiltered.toString()
                + ", rank="
                + rankFiltered.toString()
                + "]";
    }
}
