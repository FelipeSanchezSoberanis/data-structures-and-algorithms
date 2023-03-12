import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class PhoneBook {
    private HashMap<Integer, String> contactData = new HashMap<>();
    private FastScanner in = new FastScanner();

    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private void processQuery(Query query) {
        if (query.getType().equals("add")) {
            contactData.put(query.getNumber(), query.getName());
        } else if (query.getType().equals("del")) {
            if (contactData.containsKey(query.getNumber())) {
                contactData.remove(query.getNumber());
            }
        } else {
            String response =
                    contactData.containsKey(query.getNumber())
                            ? contactData.get(query.getNumber())
                            : "not found";
            writeResponse(response);
        }
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) processQuery(readQuery());
    }
}

class Query {
    private String type;
    private String name;
    private int number;

    public Query(String type, String name, int number) {
        this.type = type;
        this.name = name;
        this.number = number;
    }

    public Query(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

class FastScanner {
    private BufferedReader br;
    private StringTokenizer st;

    FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }
}
