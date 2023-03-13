import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;

public class Substr {
    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String s = in.next();
        int q = in.nextInt();
        Solver solver = new Solver(s);
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            out.println(solver.ask(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Substr().run();
    }
}

class Solver {
    private String s;
    private long m1;
    private long m2;
    private long x;
    private long[] h1;
    private long[] h2;

    public Solver(String s) {
        this.s = s;
        this.m1 = 1000000000L + 7L;
        this.m2 = 1000000000L + 9L;
        this.x = new Random(-1).nextLong(1000000000L) + 1;

        precomputeHashes();
    }

    private void precomputeHashes() {
        h1 = new long[s.length()];
        h2 = new long[s.length()];

        h1[0] = 0;
        h2[0] = 0;

        for (int i = 1; i < h1.length; i++) {
            h1[i] = (x * h1[i - 1] + s.charAt(i)) % m1;
            h2[i] = (x * h2[i - 1] + s.charAt(i)) % m2;
        }
    }

    private long H(long[] h, int a, int l) {
        return h[a + l - 1] - x * h[a];
    }

    public boolean ask(int a, int b, int l) {
        return H(h1, a, l) % m1 == H(h2, b, l) % m1 && H(h1, a, l) % m2 == H(h2, b, l) % m2;
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
