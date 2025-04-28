package mg.itu.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeAnalyzer {
    private Map<Character, String> codeTable;

    public CodeAnalyzer(Map<Character, String> codeTable) {
        if (codeTable == null) {
            throw new IllegalArgumentException("code table cannot be null");
        }
        this.codeTable = codeTable;
    }

    public Map<Integer, String> getCodeLengthDistribution() {
        Map<Integer, String> distribution = new HashMap<>();
        for (Map.Entry<Character, String> entry : codeTable.entrySet()) {
            int length = entry.getValue().length();
            distribution.merge(length, entry.getKey().toString(),
                (oldValue, newValue) -> oldValue + "," + newValue);
        }
        return distribution;
    }

    public String getCharactersByCodeLength(int length) {
        return codeTable.entrySet().stream()
            .filter(entry -> entry.getValue().length() == length)
            .map(entry -> entry.getKey().toString())
            .collect(Collectors.joining(","));
    }
}