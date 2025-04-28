package mg.itu.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mg.itu.huffman.CodeAnalyzer;
import mg.itu.huffman.HuffmanFileReader;
import mg.itu.huffman.HuffmanTree;
import mg.itu.huffman.SardinasPatterson;

public class TestHuffman {
    
    private static void createTestFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    private static boolean testFrequencies(Map<Character, Integer> frequencies) {
        // expected frequencies for "hello world"
        return frequencies.getOrDefault('h', 0) == 1 &&
               frequencies.getOrDefault('e', 0) == 1 &&
               frequencies.getOrDefault('l', 0) == 3 &&
               frequencies.getOrDefault('o', 0) == 2 &&
               frequencies.getOrDefault(' ', 0) == 1 &&
               frequencies.getOrDefault('w', 0) == 1 &&
               frequencies.getOrDefault('r', 0) == 1 &&
               frequencies.getOrDefault('d', 0) == 1 &&
               frequencies.size() == 8;
    }

    private static boolean testHuffmanTree(Map<Character, String> codeTable) {
        // check if code table is generated and codes are non-empty
        if (codeTable == null || codeTable.isEmpty()) return false;

        for (String code : codeTable.values()) 
        { if (code == null || code.isEmpty()) return false; }
        
        // verify prefix property (no code is prefix of another)
        for (Map.Entry<Character, String> firstEntry : codeTable.entrySet()) {
            for (Map.Entry<Character, String> secondEntry : codeTable.entrySet()) {
                if (firstEntry.getKey() != secondEntry.getKey() && 
                    secondEntry.getValue().startsWith(firstEntry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean testCodeAnalyzer(Map<Integer, String> distribution, String charsLength2) {
        // check if distribution is non-empty and contains valid lengths
        if (distribution == null || distribution.isEmpty()) return false;
        
        // for "hello world", expect some codes with length 2 or more
        return !charsLength2.isEmpty() || distribution.containsKey(2);
    }

    public static void main(String[] args) {
        try {
            // create a test file
            String testFilePath = "test.txt";
            createTestFile(testFilePath, "hello world");

            // test FileReader
            HuffmanFileReader fileReader = new HuffmanFileReader();
            Map<Character, Integer> frequencies = fileReader.countFrequencies(testFilePath);
            System.out.println("Frequency Test: " + (testFrequencies(frequencies) ? "PASSED" : "FAILED"));

            // test HuffmanTree
            HuffmanTree huffmanTree = new HuffmanTree(frequencies);
            Map<Character, String> codeTable = huffmanTree.getCodeTable();
            System.out.println("HuffmanTree Test: " + (testHuffmanTree(codeTable) ? "PASSED" : "FAILED"));

            // test CodeAnalyzer
            CodeAnalyzer analyzer = new CodeAnalyzer(codeTable);
            Map<Integer, String> distribution = analyzer.getCodeLengthDistribution();
            String charsLength2 = analyzer.getCharactersByCodeLength(2);
            System.out.println("CodeAnalyzer Test: " + (testCodeAnalyzer(distribution, charsLength2) ? "PASSED" : "FAILED"));

            // test SardinasPatterson
            SardinasPatterson sp = new SardinasPatterson();
            List<String> validCode = Arrays.asList("00", "010", "10", "110", "111");
            List<String> invalidCode = Arrays.asList("1", "00", "01", "10");
            boolean validResult = sp.isValidPrefixCode(validCode);
            boolean invalidResult = sp.isValidPrefixCode(invalidCode);
            System.out.println("SardinasPatterson Test: " + 
                (validResult && !invalidResult ? "PASSED" : "FAILED"));

        } catch (IOException e) {
            System.out.println("Test failed due to IOException: " + e.getMessage());
        }
    }
}