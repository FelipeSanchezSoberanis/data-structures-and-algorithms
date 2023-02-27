import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BracketsInCode {
    private static final Logger LOGGER = Logger.getLogger(BracketsInCode.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

    private static class SimpleStack<T> extends Stack<T> {
        private List<T> items;
        private int counter;

        public SimpleStack(int itemsSize) {
            this.items = new ArrayList<>(itemsSize);
            this.counter = 0;
        }

        @Override
        public T push(T item) {
            items.add(counter, item);
            counter++;
            return item;
        }

        @Override
        public synchronized T pop() {
            counter--;
            return items.remove(counter);
        }

        @Override
        public synchronized boolean isEmpty() {
            return items.isEmpty();
        }

        @Override
        public synchronized T peek() {
            return items.get(counter - 1);
        }
    }

    private static class CharacterWithPosition {
        private int index;
        private Character character;

        public CharacterWithPosition(int index, Character character) {
            this.index = index;
            this.character = character;
        }

        public int getIndex() {
            return index;
        }

        public Character getCharacter() {
            return character;
        }

        @Override
        public String toString() {
            return "CharacterWithPosition [character=" + character + ", index=" + index + "]";
        }
    }

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
        SimpleStack<CharacterWithPosition> stack = new SimpleStack<>(str.length());
        char[] strArray = str.toCharArray();

        for (int i = 0; i < strArray.length; i++) {
            CharacterWithPosition currentCharWithPosition =
                    new CharacterWithPosition(i, strArray[i]);

            Character currentChar = currentCharWithPosition.getCharacter();
            if (currentChar.equals('(') || currentChar.equals('[') || currentChar.equals('{')) {
                stack.push(currentCharWithPosition);
            } else if (currentChar.equals(')')
                    || currentChar.equals(']')
                    || currentChar.equals('}')) {
                if (stack.isEmpty()) return new Result(i, false);

                CharacterWithPosition topCharWithPosition = stack.pop();
                Character topChar = topCharWithPosition.getCharacter();

                if ((currentChar.equals(')') && !topChar.equals('('))
                        || (currentChar.equals(']') && !topChar.equals('['))
                        || (currentChar.equals('}') && !topChar.equals('{'))) {
                    return new Result(i, false);
                }
            }

            LOGGER.info(String.format("Index: %s", i));
            LOGGER.info(String.format("Current character: %s", currentCharWithPosition));
            LOGGER.info(String.format("Stack: %s", stack.toString()));
        }

        return new Result(stack.isEmpty() ? 0 : stack.peek().getIndex(), stack.isEmpty());
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        scanner.close();

        Long start = System.currentTimeMillis();
        Result result = isBalanced(input);
        Long end = System.currentTimeMillis();

        LOGGER.info(String.format("Time: %s ms", (end - start)));

        System.out.println(result.isBalanced() ? "Success" : result.getIndex() + 1);
    }
}
