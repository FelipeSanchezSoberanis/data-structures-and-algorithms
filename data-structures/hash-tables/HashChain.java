import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChain {
    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChain().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i) hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        switch (query.getType()) {
            case "add":
                if (!elems.contains(query.getS())) elems.add(0, query.getS());
                break;
            case "del":
                if (elems.contains(query.getS())) elems.remove(query.getS());
                break;
            case "find":
                writeSearchResult(elems.contains(query.getS()));
                break;
            case "check":
                for (String cur : elems) if (hashFunc(cur) == query.getInd()) out.print(cur + " ");
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.getType());
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }
}

class Query {
    private String type;
    private String s;
    private int ind;

    public Query(String type, String s) {
        this.type = type;
        this.s = s;
    }

    public Query(String type, int ind) {
        this.type = type;
        this.ind = ind;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
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
