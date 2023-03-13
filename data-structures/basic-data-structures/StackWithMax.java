import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class StackWithMax {
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

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> trackingStack = new Stack<Integer>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);

                if (trackingStack.isEmpty() || value >= trackingStack.peek())
                    trackingStack.push(value);

            } else if ("pop".equals(operation)) {
                int removedValue = stack.pop();

                if (removedValue == trackingStack.peek()) trackingStack.pop();

            } else if ("max".equals(operation)) {
                System.out.println(trackingStack.peek());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new StackWithMax().solve();
    }
}
