import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TreeHeight {
    private static final Logger LOGGER = Logger.getLogger(TreeHeight.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static class Node {
        private int value;

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }
    }

    private static class Tree {
        private Map<Integer, List<Node>> nodes;

        public Tree(int[] nodeParentValues) {
            nodes = new HashMap<>();

            for (int i = 0; i < nodeParentValues.length; i++) {
                Node node = new Node(i);
                int nodeParentValue = nodeParentValues[i];

                List<Node> childrenNodes =
                        nodes.containsKey(nodeParentValue)
                                ? nodes.get(nodeParentValue)
                                : new ArrayList<>();
                childrenNodes.add(node);

                nodes.put(nodeParentValue, childrenNodes);

                LOGGER.info(String.format("Creating tree. Working on node: %s", node.toString()));
            }
        }

        private Node getRootNode() {
            return nodes.get(-1).get(0);
        }

        @Override
        public String toString() {
            return "Tree [nodes=" + nodes + "]";
        }

        public int getHeight(Node node) {
            if (!nodes.containsKey(node.getValue())) {
                LOGGER.info(String.format("Node %s has no children", node.toString()));
                return 1;
            }

            List<Node> childrenNodes = nodes.get(node.getValue());
            List<Integer> childrenNodesHeights =
                    childrenNodes.stream().map(n -> getHeight(n)).collect(Collectors.toList());

            LOGGER.info(
                    String.format(
                            "Node %s has %s children", node.toString(), childrenNodes.size()));

            return 1 + Collections.max(childrenNodesHeights);
        }
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

        Tree tree = new Tree(nodes);
        Node rootNode = tree.getRootNode();
        int treeHeight = tree.getHeight(rootNode);

        LOGGER.info(String.format("Tree: %s", tree.toString()));
        LOGGER.info(String.format("Tree height: %s", treeHeight));

        System.out.println(treeHeight);
    }
}
