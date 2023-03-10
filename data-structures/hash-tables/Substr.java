import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
    private MyLogger LOGGER;

    private String s;
    private long m1;
    private long m2;
    private long x;
    private long[] h1;
    private long[] h2;

    public Solver(String s) {
        this.LOGGER = new MyLogger(Solver.class.getName());

        this.s = s;
        this.m1 = 1000000000L + 7L;
        this.m2 = 1000000000L + 9L;
        this.x = new Random().nextLong() + 1;

        precomputeHashes();
    }

    private void precomputeHashes() {
        h1 = new long[s.length() + 1];
        h2 = new long[s.length() + 1];

        h1[0] = 0;
        h2[0] = 0;

        for (int i = 1; i <= s.length(); i++) {
            h1[i] = (x * h1[i - 1] + s.charAt(i - 1)) % m1;
            h2[i] = (x * h2[i - 1] + s.charAt(i - 1)) % m2;
        }

        LOGGER.infoFormat("h1: %s", Arrays.toString(h1));
        LOGGER.infoFormat("h2: %s", Arrays.toString(h2));
    }

    private long H(long[] h, int a, int l) {
        return Math.abs(h[a + l] - x ^ l * h[a]);
    }

    public boolean ask(int a, int b, int l) {
        LOGGER.infoFormat("Substring 1: %s", s.substring(a, a + l));
        LOGGER.infoFormat("Substring 2: %s", s.substring(b, b + l));
        LOGGER.infoFormat("H(h1, a, l) %% m1: %s", H(h1, a, l) % m1);
        LOGGER.infoFormat("H(h2, a, l) %% m2: %s", H(h1, b, l) % m1);

        return s.substring(a, a + l).hashCode() == s.substring(b, b + l).hashCode();
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

class MyLogger {
    private Logger logger;
    private Level loggerLevel;

    public MyLogger(String className) {
        this.logger = Logger.getLogger(className);
        this.loggerLevel = Level.OFF;

        configureLogger();
    }

    private void configureLogger() {
        logger.setLevel(loggerLevel);

        logger.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%s] - %s", logRecord.getLevel(), logRecord.getMessage());
                    }
                };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);

        logger.addHandler(consoleHandler);
    }

    public void infoFormat(String stringToFormat, Object... variables) {
        logger.info(String.format(stringToFormat + "\n", variables));
    }
}
