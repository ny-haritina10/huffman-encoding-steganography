package mg.itu.huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffmanFileReader {
        
    public Map<Character, Integer> countFrequencies(String filePath) throws IOException {
        Map<Character, Integer> frequencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int charCode;
            while ((charCode = reader.read()) != -1) {
                char c = (char) charCode;
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }
        }
        return frequencies;
    }
}