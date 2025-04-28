package mg.itu.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private class Node implements Comparable<Node> {
        char character;
        int frequency;
        Node left, right;

        Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        Node(int frequency, Node left, Node right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency;
        }
    }

    private Node root;
    private Map<Character, String> codeTable;

    public HuffmanTree(Map<Character, Integer> frequencies) {
        if (frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("frequencies cannot be empty");
        }
        buildTree(frequencies);
        codeTable = new HashMap<>();
        generateCodes(root, "");
    }

    private void buildTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.offer(new Node(entry.getKey(), entry.getValue()));
        }

        // handle single character case
        if (queue.size() == 1) {
            root = new Node('\0', queue.poll().frequency);
            return;
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            queue.offer(new Node(left.frequency + right.frequency, left, right));
        }
        root = queue.poll();
    }

    private void generateCodes(Node node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            codeTable.put(node.character, code.isEmpty() ? "0" : code);
            return;
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public Map<Character, String> getCodeTable() {
        return codeTable;
    }
}