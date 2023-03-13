import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public Solver(String s) {
        this.s = s;
    }

    public boolean ask(int a, int b, int l) {
        return s.substring(a, a + l).equals(s.substring(b, b + l));
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
