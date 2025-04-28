package mg.itu.steganography;

public class RecurrenceSequence {

    private final int a;
    private final int b;
    private final int modulus;
    private final int initialValue;

    public RecurrenceSequence(int a, int b, int modulus, int initialValue) {
        if (modulus <= 0) 
        { throw new IllegalArgumentException("modulus must be positive"); }

        this.a = a;
        this.b = b;
        this.modulus = modulus;
        this.initialValue = initialValue;
    }

    public int[] generateSequence(int length) {
        if (length < 0) 
        { throw new IllegalArgumentException("length must be non-negative"); }
        
        int[] sequence = new int[length];
        if (length == 0) return sequence;
        
        sequence[0] = initialValue;
        for (int i = 1; i < length; i++) {
            sequence[i] = (a * sequence[i - 1] + b) % modulus;
        }
        return sequence;
    }
}