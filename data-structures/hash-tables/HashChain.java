import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class HashChain {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private HashMap<Integer, LinkedList<String>> map;
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
        int hash;
        LinkedList<String> contacts;
        switch (query.getType()) {
            case "add":
                hash = hashFunc(query.getS());

                contacts = map.containsKey(hash) ? map.get(hash) : new LinkedList<>();

                if (!contacts.stream()
                        .filter(c -> c.equals(query.getS()))
                        .findFirst()
                        .isPresent()) {
                    contacts.addFirst(query.getS());
                    map.put(hash, contacts);
                }

                break;
            case "del":
                hash = hashFunc(query.getS());

                if (map.containsKey(hash)) {
                    contacts = map.get(hash);
                    contacts.remove(query.getS());

                    if (contacts.isEmpty()) {
                        map.remove(hash);
                    } else {
                        map.put(hash, contacts);
                    }
                }
                break;
            case "find":
                hash = hashFunc(query.getS());
                writeSearchResult(
                        map.containsKey(hash)
                                && map.get(hash).stream()
                                        .filter(f -> f.equals(query.getS()))
                                        .findFirst()
                                        .isPresent());
                break;
            case "check":
                if (map.containsKey(query.getInd())) {
                    map.get(query.getInd()).forEach(c -> out.print(c + " "));
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.getType());
        }
    }

    public void processQueries() throws IOException {
        map = new HashMap<>();
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
