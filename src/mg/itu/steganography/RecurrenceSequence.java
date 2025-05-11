package mg.itu.steganography;

import java.util.function.Function;

@FunctionalInterface
interface RecurrenceFunction {
    int apply(int[] sequence, int index, int modulus);
}

public class RecurrenceSequence {

    private final RecurrenceFunction function;
    private final int modulus;
    private final int initialValue;

    // constructor for arbitrary recurrence function
    public RecurrenceSequence(RecurrenceFunction function, int modulus, int initialValue) {
        if (function == null) {
            throw new IllegalArgumentException("recurrence function cannot be null");
        }
        if (modulus <= 0) {
            throw new IllegalArgumentException("modulus must be positive");
        }
        this.function = function;
        this.modulus = modulus;
        this.initialValue = initialValue;
    }

    // constructor for default linear recurrence: Un+1 = (a * Un + b) mod modulus
    public RecurrenceSequence(int a, int b, int modulus, int initialValue) {
        this((sequence, index, mod) -> (a * sequence[index - 1] + b) % mod, modulus, initialValue);
    }

    public int[] generateSequence(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be non-negative");
        }
        int[] sequence = new int[length];
        if (length == 0) {
            return sequence;
        }
        
        sequence[0] = initialValue;
        for (int i = 1; i < length; i++) {
            sequence[i] = function.apply(sequence, i, modulus);
            // ensure non-negative values
            sequence[i] = (sequence[i] % modulus + modulus) % modulus;
        }
        return sequence;
    }
}



// comments: examples of how to modify the recurrence function
/*
* To use a different recurrence relation, pass a custom RecurrenceFunction to the constructor.
* The function takes the current sequence, the index of the next value, and the modulus.
* Below are examples of various recurrence relations:
*
* 1. Linear Recurrence (default): Un+1 = (a * Un + b) mod modulus
*    Example: Un+1 = (2 * Un + 1) mod 4
*    Usage:
*    RecurrenceSequence seq = new RecurrenceSequence(2, 1, 4, 0);
*    // or explicitly:
*    RecurrenceSequence seq = new RecurrenceSequence(
*        (sequence, index, mod) -> (2 * sequence[index - 1] + 1) % mod,
*        4, 0
*    );
*    Output for length=4: [0, 1, 3, 3]
*
* 2. Quadratic Recurrence: Un+1 = (Un^2 + c) mod modulus
*    Example: Un+1 = (Un^2 + 1) mod 5
*    Usage:
*    RecurrenceSequence seq = new RecurrenceSequence(
*        (sequence, index, mod) -> (sequence[index - 1] * sequence[index - 1] + 1) % mod,
*        5, 2
*    );
*    Output for length=4: [2, 0, 1, 2]
*
* 3. Higher-Order Recurrence: Un+1 = (Un + Un-1) mod modulus (Fibonacci-like)
*    Example: Un+1 = (Un + Un-1) mod 7
*    Usage:
*    RecurrenceSequence seq = new RecurrenceSequence(
*        (sequence, index, mod) -> {
*            if (index < 2) return sequence[index - 1]; // handle early indices
*            return (sequence[index - 1] + sequence[index - 2]) % mod;
*        },
*        7, 1
*    );
*    Output for length=5: [1, 1, 2, 3, 5]
*
* 4. Custom Recurrence: Un+1 = (2 * Un + Un mod 2) mod modulus
*    Example: Un+1 = (2 * Un + (Un % 2)) mod 6
*    Usage:
*    RecurrenceSequence seq = new RecurrenceSequence(
*        (sequence, index, mod) -> (2 * sequence[index - 1] + (sequence[index - 1] % 2)) % mod,
*        6, 3
*    );
*    Output for length=4: [3, 1, 3, 1]
*
* Notes:
* - Ensure the function handles edge cases (e.g., index=1 for higher-order recurrences).
* - The modulus is applied automatically to keep values in [0, modulus-1].
* - Use the sequence array to access previous values (e.g., sequence[index-2] for Un-1).
*/