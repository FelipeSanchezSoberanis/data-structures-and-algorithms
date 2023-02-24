import java.util.Scanner;
import java.util.Stack;

public class BracketsInCode {
    private static class Result {
        private int index;
        private boolean isBalanced;

        public Result(int index, boolean isBalanced) {
            this.index = index;
            this.isBalanced = isBalanced;
        }

        public int getIndex() {
            return index;
        }

        public boolean isBalanced() {
            return isBalanced;
        }
    }

    private static Result isBalanced(String str) {
        Stack<Character> stack = new Stack<>();
        char[] strArray = str.toCharArray();

        for (int i = 0; i < strArray.length; i++) {
            Character _char = strArray[i];

            if (_char.equals('(') || _char.equals('[') || _char.equals('{')) {
                stack.push(_char);
            } else {
                if (stack.empty()) return new Result(i, false);

                Character top = stack.pop();

                if ((top.equals('[') && !_char.equals(']'))
                        || (top.equals('(') && !_char.equals(')'))
                        || (top.equals('{') && !_char.equals('}'))) {

                    return new Result(i, false);
                }
            }
        }

        return new Result(0, stack.isEmpty());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        scanner.close();

        Result result = isBalanced(input);

        System.out.println(result.isBalanced() ? "Success" : result.getIndex() + 1);
    }
}
