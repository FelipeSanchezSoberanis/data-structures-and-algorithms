import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {
    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static long polyHash(String s, int p, int x) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            hash = (hash * x + s.charAt(i)) % p;
        }
        return hash;
    }

    private static List<Long> precomputeHashes(String T, int P, int p, int x) {
        List<Long> H = new ArrayList<>(T.length() - P + 1);
        for (int i = 0; i < T.length() - P + 1; i++) {
            H.add(0L);
        }

        String S = T.substring(T.length() - P, T.length());
        H.set(T.length() - P, polyHash(S, p, x));

        long y = 1;
        for (int i = 1; i <= P; i++) {
            y = (y * x) % p;
        }
        for (int i = T.length() - P - 1; i >= 0; i--) {
            H.set(i, (x * H.get(i + 1) + T.charAt(i) - y * T.charAt(i + P)) % p);
        }

        return H;
    }

    private static List<Integer> getOccurrences(Data input) {
        String P = input.getPattern();
        String T = input.getText();

        int p = 1000000007;
        int x = new Random().nextInt(p - 1) + 1;
        List<Integer> positions = new ArrayList<>();
        long pHash = polyHash(P, p, x);
        List<Long> H = precomputeHashes(T, P.length(), p, x);

        for (int i = 0; i < T.length() - P.length() + 1; i++) {
            if (pHash != H.get(i)) continue;

            if (T.substring(i, i + P.length()).equals(P)) positions.add(i);
        }
        return positions;
    }
}

class Data {
    private String pattern;
    private String text;

    public Data(String pattern, String text) {
        this.pattern = pattern;
        this.text = text;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class FastScanner {
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
