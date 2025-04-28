package mg.itu.test;

import java.util.Arrays;

import mg.itu.steganography.ImageProcessor;
import mg.itu.steganography.ImageSteganography;
import mg.itu.steganography.RecurrenceSequence;

public class TestSteganography {
    public static boolean testSteganography(String imagePath, String expectedMessage) {
        try {
            // setup recurrence sequence: Un+1 = (2Un + 1) % 4, U0 = 0
            RecurrenceSequence sequence = new RecurrenceSequence(2, 1, 4, 0);
            ImageProcessor processor = new ImageProcessor();
            processor.loadImage(imagePath);

            // extract message of length 4
            ImageSteganography stego = new ImageSteganography(processor, sequence);
            String message = stego.extractMessage(4);

            // debug: print sequence and message
            int[] positions = sequence.generateSequence(4);
            System.out.println("Recurrence sequence positions: " + Arrays.toString(positions));
            System.out.println("Message length: " + message.length());
            System.out.println("Extracted message: " + message);

            // check message length and expected message (if provided)
            boolean isValid = message.length() == 4;
            if (expectedMessage != null) {
                isValid = isValid && message.equals(expectedMessage);
            }
            return isValid;
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // test with user image (no specific expected message, just check execution)
        System.out.println("\n=== Testing with User Image ===");
        String userImage = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\_tp-final-codage\\assets\\img\\black-white.jpg";

        boolean userImageResult = testSteganography(userImage, null);
        System.out.println("User Image Test: " + (userImageResult ? "PASSED" : "FAILED"));
    }
}