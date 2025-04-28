package mg.itu.huffman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SardinasPatterson {
    public boolean isValidPrefixCode(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return false;
        }

        // initialize l0 as the input language
        List<Set<String>> lSets = new ArrayList<>();
        lSets.add(new HashSet<>(codes));

        // compute l1 = l0^-1 * l0 - {epsilon}
        Set<String> l1 = new HashSet<>();
        for (String code1 : codes) {
            for (String code2 : codes) {
                if (code1.equals(code2)) continue;
                if (code1.startsWith(code2)) {
                    String suffix = code1.substring(code2.length());
                    if (!suffix.isEmpty()) { // exclude epsilon
                        l1.add(suffix);
                    }
                }
            }
        }
        lSets.add(l1);

        // compute ln+1 = l0^-1 * ln ∪ ln^-1 * l0 for n >= 1
        while (!lSets.get(lSets.size() - 1).isEmpty()) {
            Set<String> lastSet = lSets.get(lSets.size() - 1);
            Set<String> nextSet = new HashSet<>();

            // compute l0^-1 * ln
            for (String suffix : lastSet) {
                for (String code : codes) {
                    String concat = suffix + code;
                    for (String code2 : codes) {
                        if (concat.startsWith(code2)) {
                            String residue = concat.substring(code2.length());
                            if (residue.isEmpty()) {
                                return false; // epsilon found, not a code
                            }
                            nextSet.add(residue);
                        }
                    }
                }
            }

            // compute ln^-1 * l0
            for (String code1 : codes) {
                for (String suffix : lastSet) {
                    if (code1.startsWith(suffix)) {
                        String residue = code1.substring(suffix.length());
                        if (residue.isEmpty()) {
                            return false; // epsilon found, not a code
                        }
                        nextSet.add(residue);
                    }
                }
            }

            // check for cycle or repeated set
            if (lSets.contains(nextSet)) {
                return true; // cycle detected, no epsilon found, is a code
            }

            lSets.add(nextSet);
        }

        return true; // no epsilon found in any ln, is a code
    }
}