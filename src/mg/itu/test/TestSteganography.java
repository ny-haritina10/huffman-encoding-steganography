package mg.itu.test;

import java.util.Arrays;

import mg.itu.steganography.ImageProcessor;
import mg.itu.steganography.ImageSteganography;
import mg.itu.steganography.ImageSteganographyEncoder;
import mg.itu.steganography.RecurrenceSequence;

public class TestSteganography {
    
    public static boolean testSteganography(String inputImagePath, String outputImagePath, String messageToEncode) {
        try {
            // setup recurrence sequence: Un+1 = (2Un + 1) % 4, U0 = 0
            RecurrenceSequence sequence = new RecurrenceSequence(2, 1, 4, 0);
            ImageProcessor processor = new ImageProcessor();
            processor.loadImage(inputImagePath);

            // encode message
            ImageSteganographyEncoder encoder = new ImageSteganographyEncoder(processor, sequence);
            encoder.encodeMessage(messageToEncode, outputImagePath);

            // decode message from encoded image
            ImageProcessor decodeProcessor = new ImageProcessor();
            decodeProcessor.loadImage(outputImagePath);
            ImageSteganography decoder = new ImageSteganography(decodeProcessor, sequence);
            String decodedMessage = decoder.extractMessage(messageToEncode.length());

            // debug: print sequence and messages
            int[] positions = sequence.generateSequence(messageToEncode.length());
            System.out.println("Recurrence sequence positions: " + Arrays.toString(positions));
            System.out.println("Encoded message: " + messageToEncode);
            System.out.println("Decoded message: " + decodedMessage);

            // verify message length and content
            return decodedMessage.length() == messageToEncode.length() &&
                   decodedMessage.equals(messageToEncode);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("\n=== Testing with User Image ===");
        String userImage = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/assets/img/red.png";
        String userOutputImage = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/output/output.png";
        boolean userImageResult = testSteganography(userImage, userOutputImage, "0011"); 
        System.out.println("User Image Test: " + (userImageResult ? "PASSED" : "FAILED"));
    }
}