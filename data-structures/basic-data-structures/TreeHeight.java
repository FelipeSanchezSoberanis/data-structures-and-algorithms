import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TreeHeight {
    private static final Logger LOGGER = Logger.getLogger(TreeHeight.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    public static class Node {
        private int value;
        private int parentValue;
        private List<Node> children;

        public Node(int value, int parentValue) {
            this.value = value;
            this.parentValue = parentValue;
        }

        public int getParentValue() {
            return parentValue;
        }

        public void setParentValue(int parentValue) {
            this.parentValue = parentValue;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }

        public void addChild(Node node) {
            this.children.add(node);
        }

        @Override
        public String toString() {
            return "{children:"
                    + children
                    + ", parentValue:"
                    + parentValue
                    + ", value:"
                    + value
                    + "}";
        }
    }

    private static Node createTree(int[] nodeInts) {
        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i < nodeInts.length; i++) {
            nodes.add(new Node(i, nodeInts[i]));
        }

        for (Node node : nodes) {
            node.setChildren(
                    nodes.stream()
                            .filter(n -> n.getParentValue() == node.getValue())
                            .collect(Collectors.toList()));
        }

        return nodes.stream().filter(n -> n.getParentValue() == -1).findFirst().get();
    }

    public static void main(String[] args) {
        LOGGER.setLevel(LOGGER_LEVEL);

        Scanner scanner = new Scanner(System.in);

        int noNodes = scanner.nextInt();
        int[] nodes = new int[noNodes];

        for (int i = 0; i < noNodes; i++) {
            nodes[i] = scanner.nextInt();
        }

        scanner.close();

        Node nodeTree = createTree(nodes);

        LOGGER.info(String.format("Tree: %s", nodeTree));
    }
}
